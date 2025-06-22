package br.org.kinflasy.api.dto.core.contact;


import br.org.kinflasy.api.apis.contacts.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAddress {
 
    private String zip;
 
    private String country;
 
    private String state;
 
    private String city;
 
    private String neighborhood;
 
    private String street;
 
    private String number;
 
    private String reference;
    
    public Address toAddress() {
        final var address = new Address();

        address.setZip(zip);
        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        address.setNeighborhood(neighborhood);
        address.setStreet(street);
        address.setNumber(number);
        address.setReference(reference);

        return address;
    }

}
