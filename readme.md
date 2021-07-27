JSR 339 (JaxRS) CDI Extension

A CDI Extension for JaxRS to have Meta Information about JaxRS Resources

```java
public class AddressForm {

    private String street;

    private String zipCode;

    private String state;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
```

```java
public class PersonForm {

    private String firstName;

    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
```

```java
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
```

```java
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
```

```java
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
```


