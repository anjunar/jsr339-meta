package de.bitvale.jsr339.cdi;

import de.bitvale.jsr339.Resource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class Resources {

    private final JaxRSExtension extension;

    @Inject
    public Resources(JaxRSExtension extension) {
        this.extension = extension;
    }

    public Resources() {
        this(null);
    }

    public <R> Resource<R> find(Class<R> resource) {
        return (Resource<R>) extension.getResources()
                .stream()
                .filter(res -> res.getType().getRawType().equals(resource))
                .findFirst()
                .orElse(null);
    }

    public Set<Resource<?>> getResources() {
        return extension.getResources();
    }
}
