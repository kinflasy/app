package br.org.kinflasy.api.repositories.core.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.entities.core.contact.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
