package br.org.kinflasy.api.apis.contacts.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.api.apis.contacts.entities.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
