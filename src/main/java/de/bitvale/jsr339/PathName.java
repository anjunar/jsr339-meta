package de.bitvale.jsr339;

import java.util.StringJoiner;

/**
 * @author by Patrick Bittner on 09.06.15.
 */
public class PathName {

    private final String path;

    private final String name;

    public PathName(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PathName.class.getSimpleName() + "[", "]")
                .add("path='" + path + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
