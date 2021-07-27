package de.bitvale.jsr339;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("person")
public class PersonResource {

    @Path("detail")
    @GET
    @Produces("application/json")
    public PersonForm read() {
        PersonForm personForm = new PersonForm();

        personForm.setFirstName("Mustermann");
        personForm.setLastName("Musternann");

        return personForm;
    }

    @Path("address")
    public AddressResource subResource() {
        return new AddressResource();
    }

}
