import com.google.common.base.Function;
import org.scalaby.fjava.Function2;
import org.scalaby.fjava.Lens;

import javax.annotation.Nullable;

import static org.scalaby.fjava.Lenses.*;

/**
 * User: remeniuk
 */
public class PersonZipCodeExample {

    public static void main(String[] args) {

        // you can create a lens overriding set/get methods
        Lens<Person, Address> personAddressLens = new Lens<Person, Address>() {
            @Override
            public Address get(@Nullable Person person) {
                return person.getAddress();
            }

            @Override
            public Person set(Person person, Address address) {
                return new Person(person.getFirstName(), person.getLastName(), address);
            }
        };

        // or passing function objects
        Lens<Address, Integer> addressZipCodeLens = lens(
                new Function<Address, Integer>() {
                    @Override
                    public Integer apply(@Nullable Address address) {
                        return address.getZipCode();
                    }
                },
                new Function2<Address, Integer, Address>() {
                    @Override
                    public Address apply(Address address, Integer zipCode) {
                        return new Address(address.getStreet(), address.getCity(), address.getState(), zipCode);
                    }
                }
        );

        Person person = new Person("Jack", "Smith", new Address("Default", "Default", "Default", 0));

        Lens<Person, Integer> personZipCodeLens = personAddressLens.andThen(addressZipCodeLens);

        personZipCodeLens.set(person, personZipCodeLens.get(person) + 1);

    }

}
