package de.bitvale.jsr339;

import com.google.common.collect.Sets;
import de.bitvale.introspector.type.resolved.ResolvedMethod;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class Operation {

    private final String url;

    private final String path;

    private final String httpMethod;

    private final String[] consumes;

    private final String[] produces;

    private final ResolvedMethod<?> method;

    private final String name;

    private final Resource<?> resource;

    public Operation(String url, String httpMethod, String[] consumes, String[] produces, ResolvedMethod<?> method, String name, Resource<?> resource) {
        this.httpMethod = httpMethod;
        this.consumes = consumes;
        this.produces = produces;
        this.method = method;
        this.name = name;
        this.resource = resource;
        this.url = url;
        this.path = getDenormalizedUrls().stream().findFirst().get().getPath();
    }

    public Set<PathName> getDenormalizedUrls() {

        final Set<Locator> parents = resource.getParents();

        if (parents.isEmpty()) {
            return Sets.newHashSet(new PathName(getUrl(), getName()));
        }

        final Set<PathName> denormalizedUrlsIntern = getDenormalizedUrlsIntern(parents);

        final Set<PathName> denormalizedUrls = new HashSet<>();

        for (PathName path : denormalizedUrlsIntern) {

            denormalizedUrls.add(new PathName(path.getPath() + "/" + getUrl(), path.getName() + " " + getName()));

        }

        return denormalizedUrls;

    }

    private Set<PathName> getDenormalizedUrlsIntern(Set<Locator> parents) {

        final Set<PathName> denormalizedUrls = new HashSet<>();

        for (Locator parent : parents) {

            String path = parent.getPath();
            String name = parent.getName();

            Resource<?> resource = parent.getResource();

            Set<PathName> denormalizedUrlsIntern = getDenormalizedUrlsIntern(resource.getParents());

            if (denormalizedUrlsIntern.isEmpty()) {
                denormalizedUrls.add(new PathName(path, name));
            } else {
                for (PathName pathSegment : denormalizedUrlsIntern) {
                    denormalizedUrls.add(new PathName(pathSegment.getPath() + "/" + path, pathSegment.getName() + " " + name));
                }
            }

        }

        return denormalizedUrls;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String[] getConsumes() {
        return consumes;
    }

    public String[] getProduces() {
        return produces;
    }

    public ResolvedMethod<?> getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public Resource<?> getResource() {
        return resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(getMethod(), operation.getMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMethod());
    }

    @Override
    public String toString() {
        return "Operation{" +
                "url='" + getDenormalizedUrls() + '\'' +
                '}';
    }

}
