package de.anjunar.jsr339.cdi;

import de.anjunar.jsr339.Resource;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public interface ProcessResourceClass<X> {

    Resource<X> getResource();

    void addDefinitionError(Throwable t);

}
