package de.anjunar.jsr339;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@ApplicationScoped
public class AddressResource {

    @GET
    @Path("detail")
    @Produces("application/json")
    public AddressForm address() {
        AddressForm addressForm = new AddressForm();

        addressForm.setStreet("Somestreet 12");
        addressForm.setZipCode("22222");
        addressForm.setState("Somewhere");

        return addressForm;
    }

}
