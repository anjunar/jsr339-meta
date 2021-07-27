package de.bitvale.jsr339;

import de.bitvale.jsr339.cdi.JaxRSExtension;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class CDIExtension implements TestInstancePostProcessor {


    private static final Extension EXTENSION = new JaxRSExtension();
    private static final WeldContainer CONTAINER = new Weld().addExtension(EXTENSION).initialize();
    private static final Predicate<Annotation> IS_QUALIFIER = a -> a.annotationType().isAnnotationPresent(Qualifier.class);

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws IllegalAccessException {
        for (Field field : getFields(testInstance.getClass())) {
            if (field.getAnnotation(Inject.class) != null) {
                Annotation[] qualifiers = Stream.of(field.getAnnotations())
                        .filter(IS_QUALIFIER)
                        .toArray(Annotation[]::new);
                Object injected = CONTAINER.select(field.getType(), qualifiers).get();
                field.setAccessible(true);
                field.set(testInstance, injected);
            }
        }
    }

    private List<Field> getFields(Class<?> clazzInstance) {
        List<Field> fields = new ArrayList<>();
        if (!clazzInstance.getSuperclass().equals(Object.class)) {
            fields.addAll(getFields(clazzInstance.getSuperclass()));
        } else {
            fields.addAll(asList(clazzInstance.getDeclaredFields()));
        }
        return fields;
    }

}