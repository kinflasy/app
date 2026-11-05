package br.org.kinflasy.apis.contacts.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.kinflasy.apis.contacts.entities.EmailAddress;

public interface EmailAddressRepository extends JpaRepository<EmailAddress, UUID> {

    public List<EmailAddress> findByAddress(String address);

}
