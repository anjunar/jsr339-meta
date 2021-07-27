package de.bitvale.jsr339;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author by Patrick Bittner on 09.06.15.
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
@Documented
public @interface Name {

    String value();

}
