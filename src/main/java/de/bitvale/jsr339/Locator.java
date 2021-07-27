package de.bitvale.jsr339;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class Locator {

    private final String path;

    private final String name;

    private final Set<Resource<?>> types = new HashSet<>();

    private final Resource<?> resource;

    public Locator(String path, String name, Resource<?> resource) {
        this.path = path;
        this.name = name;
        this.resource = resource;
    }

    public boolean add(Resource resource) {
        return types.add(resource);
    }


    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Set<Resource<?>> getTypes() {
        return types;
    }

    public Resource<?> getResource() {
        return resource;
    }

}
