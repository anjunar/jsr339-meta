package de.anjunar.jsr339;

import de.anjunar.jsr339.cdi.Resources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(CDIExtension.class)
public class TestCase {

    @Inject
    Resources container;

    @Test
    public void test() {

        Resource<PersonResource> personResource = container.find(PersonResource.class);

        for (Operation operation : personResource.getOperations()) {
            assertEquals("person/detail", operation.getPath());
        }

        Resource<AddressResource> addressResource = personResource.find(AddressResource.class);

        for (Operation operation : addressResource.getOperations()) {
            assertEquals("person/address/detail", operation.getPath());
        }

    }

}
