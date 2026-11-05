package br.org.kinflasy.apis.contacts.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.org.kinflasy.apis.contacts.entities.EmailAddress;
import br.org.kinflasy.apis.contacts.repositories.EmailAddressRepository;
import br.org.kinflasy.libs.contacts.dto.EmailAddressDto;
import br.org.kinflasy.libs.contacts.dto.EmailAddressRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailAddressService {

    private final ModelMapper mapper;

    private final EmailAddressRepository emailAddressRepository;

    // Todo atualizar FGA

    public EmailAddressDto create(final EmailAddressRequest request) {
        final var entity = new EmailAddress();
        entity.setAddress(request.getAddress());

        final var saved = emailAddressRepository.save(entity);

        return mapper.map(saved, EmailAddressDto.class);
    }

    public Optional<EmailAddressDto> findById(final UUID id) {
        return emailAddressRepository.findById(id)
                .map(entity -> mapper.map(entity, EmailAddressDto.class));
    }

    public List<EmailAddressDto> findByAddress(final String address) {
        return emailAddressRepository.findByAddress(address).stream()
                .map(entity -> mapper.map(entity, EmailAddressDto.class))
                .toList();
    }

    public void delete(final UUID id) {
        emailAddressRepository.deleteById(id);
    }

}
