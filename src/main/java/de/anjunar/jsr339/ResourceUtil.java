package de.anjunar.jsr339;

import de.bitvale.introspector.type.resolved.ResolvedMethod;
import de.bitvale.introspector.type.resolved.ResolvedType;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class ResourceUtil {

    private static final Map<ResolvedType<?>, Resource<?>> cache = new HashMap<>();

    public static <X>Resource<X> process(ResolvedType<X> resourceType, Locator locator, Set<ResolvedType<?>> subResourceTypes) {
        @SuppressWarnings("unchecked")
        Resource<X> resource = (Resource<X>) cache.get(resourceType);
        if (resource == null) {
            resource = new Resource<>(resourceType);
            cache.put(resourceType, resource);
        }
        if (locator != null) {
            resource.addParent(locator);
        }
        final Path topLevelPath = resourceType.getAnnotation(Path.class);
        final Name topLevelName = resourceType.getAnnotation(Name.class);
        for (ResolvedMethod<?> resolvedMethod : resourceType.getMethods()) {
            final String httpMethod = httpMethod(resolvedMethod);
            final Path path = resolvedMethod.getAnnotation(Path.class);
            final Consumes consumes = resolvedMethod.getAnnotation(Consumes.class);
            final Produces produces = resolvedMethod.getAnnotation(Produces.class);
            final Name named = resolvedMethod.getAnnotation(Name.class);

            String name = "";
            if (topLevelName != null && named != null) {
                name = topLevelName.value() + " " + named.value();
            }

            if (topLevelName != null && named == null) {
                name = topLevelName.value();
            }

            if (topLevelName == null && named != null) {
                name = named.value();
            }

            String pathString = "";

            if (topLevelPath == null && path != null) {
                pathString = removeSlashes(path.value());
            }

            if (topLevelPath != null && path != null) {
                pathString = removeSlashes(topLevelPath.value()) + "/" + removeSlashes(path.value());
            }

            if (topLevelPath != null && path == null) {
                pathString = removeSlashes(topLevelPath.value());
            }

            if (httpMethod == null) {

                final Locator resourceLocator = new Locator(pathString, name, resource);
                resource.add(resourceLocator);
                for (ResolvedType<?> subResourceType : subResourceTypes) {
                    if (resolvedMethod.getReturnType().isSubtypeOf(subResourceType.getType())) {
                        resourceLocator.add(process(subResourceType, resourceLocator, subResourceTypes));
                    }
                }
            } else {

                resource.add(new Operation(
                        pathString,
                        httpMethod,
                        consumes == null ? null : consumes.value(),
                        produces == null ? null : produces.value(),
                        resolvedMethod,
                        name,
                        resource
                ));
            }
        }
        return resource;
    }

    public static String removeSlashes(String value) {
        return StringUtils.removeEnd(StringUtils.removeStart(value, "/"), "/");
    }

    public static String httpMethod(ResolvedMethod<?> resolvedMethod) {
        for (Annotation annotation : resolvedMethod.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(HttpMethod.class)) {
                return annotation.annotationType().getAnnotation(HttpMethod.class).value();
            }
        }
        return null;
    }
}
