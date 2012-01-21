/**
 * User: remeniuk
 */
public class Address {

    private final String street;
    private final String city;
    private final String state;
    private final Integer zipCode;

    public Address(String street, String city, String state, Integer zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

}
