package de.bitvale.jsr339.cdi;

import de.bitvale.jsr339.Resource;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class DefaultProcessResourceClass implements ProcessResourceClass {

    private final Resource<?> resource;

    private Throwable definitionError;

    public DefaultProcessResourceClass(Resource<?> resource) {
        this.resource = resource;
    }

    @Override
    public Resource<?> getResource() {
        return resource;
    }

    @Override
    public void addDefinitionError(Throwable t) {
        definitionError = t;
    }

    public Throwable getDefinitionError() {
        return definitionError;
    }
}
