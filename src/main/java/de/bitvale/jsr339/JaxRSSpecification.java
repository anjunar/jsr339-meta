package de.bitvale.jsr339;

import de.bitvale.introspector.meta.MetaSpecification;
import de.bitvale.introspector.type.resolved.ResolvedType;

import javax.ws.rs.Path;

/**
 * @author Patrick Bittner on 07.06.2015.
 */
public class JaxRSSpecification extends MetaSpecification {

    @Override
    public <X> boolean isSatisfiedBy(ResolvedType<X> type) {
        return MetaSpecification.isSatisfiedWith(type, Path.class);
    }

    @Override
    public String name() {
        return "JaxRS";
    }
}
