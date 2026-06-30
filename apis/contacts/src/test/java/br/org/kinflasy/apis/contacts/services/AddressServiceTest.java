package br.org.kinflasy.apis.contacts.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.org.kinflasy.apis.contacts.converters.AddressConverter;
import br.org.kinflasy.apis.contacts.entities.Address;
import br.org.kinflasy.apis.contacts.repositories.AddressRepository;
import br.org.kinflasy.libs.contacts.dto.AddressRequest;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    private static final UUID ADDRESS_ID = UUID.fromString("9f273aa9-c9ef-497f-b897-d7c72e1f6388");
    private static final UUID CREATED_BY = UUID.fromString("0096a83d-304f-4f14-82e1-39046c94e5fe");
    private static final String NOT_FOUND_MESSAGE = "Endereço não encontrado.";
    private static final String ZIP = "01001-000";
    private static final String CITY = "São Paulo";

    @Mock
    private AddressRepository repository;

    private AddressService service;

    @BeforeEach
    void setUp() {
        final var converter = new AddressConverter(new ModelMapper());
        service = new AddressService(repository, converter);
    }

    @Test
    void shouldCreateAddress() {
        final var request = addressRequest();

        when(repository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        final var result = service.create(request);

        final var entityCaptor = ArgumentCaptor.forClass(Address.class);
        verify(repository).save(entityCaptor.capture());

        assertAll(
                () -> assertEquals(ZIP, result.getZip()),
                () -> assertEquals(CITY, result.getCity()),
                () -> assertEquals(ZIP, entityCaptor.getValue().getZip()),
                () -> assertEquals(CITY, entityCaptor.getValue().getCity()));
    }

    @Test
    void shouldCreateAddressWithCreator() {
        final var request = addressRequest();

        when(repository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        final var result = service.create(request, CREATED_BY);

        final var entityCaptor = ArgumentCaptor.forClass(Address.class);
        verify(repository).save(entityCaptor.capture());

        assertAll(
                () -> assertEquals(ZIP, result.getZip()),
                () -> assertEquals(CITY, result.getCity()),
                () -> assertEquals(CREATED_BY, entityCaptor.getValue().getCreatedBy()));
    }

    @Test
    void shouldFindAddressById() {
        final var entity = address();

        when(repository.findById(ADDRESS_ID)).thenReturn(Optional.of(entity));

        final var result = service.findById(ADDRESS_ID);

        assertAll(
                () -> assertEquals(ADDRESS_ID, result.getId()),
                () -> assertEquals(ZIP, result.getZip()),
                () -> assertEquals(CITY, result.getCity()));
        verify(repository).findById(ADDRESS_ID);
    }

    @Test
    void shouldThrowWhenAddressIsNotFoundById() {
        when(repository.findById(ADDRESS_ID)).thenReturn(Optional.empty());

        final var exception = assertThrows(EntityNotFoundException.class, () -> service.findById(ADDRESS_ID));

        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());
        verify(repository).findById(ADDRESS_ID);
    }

    @Test
    void shouldUpdateAddress() {
        final var request = addressRequest();
        final var original = new Address();
        original.setId(ADDRESS_ID);

        when(repository.findById(ADDRESS_ID)).thenReturn(Optional.of(original));
        when(repository.save(original)).thenReturn(original);

        final var result = service.update(ADDRESS_ID, request);

        assertAll(
                () -> assertEquals(ADDRESS_ID, result.getId()),
                () -> assertEquals(ZIP, result.getZip()),
                () -> assertEquals(CITY, result.getCity()));

        final var order = inOrder(repository);
        order.verify(repository).findById(ADDRESS_ID);
        order.verify(repository).save(original);
    }

    @Test
    void shouldThrowWhenAddressToUpdateIsNotFound() {
        final var request = addressRequest();

        when(repository.findById(ADDRESS_ID)).thenReturn(Optional.empty());

        final var exception = assertThrows(EntityNotFoundException.class,
                () -> service.update(ADDRESS_ID, request));

        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());
        verify(repository).findById(ADDRESS_ID);
    }

    @Test
    void shouldDeleteAddress() {
        doNothing().when(repository).deleteById(ADDRESS_ID);

        service.delete(ADDRESS_ID);

        verify(repository).deleteById(ADDRESS_ID);
    }

    private AddressRequest addressRequest() {
        final var request = new AddressRequest();
        request.setZip(ZIP);
        request.setCity(CITY);
        return request;
    }

    private Address address() {
        final var entity = new Address();
        entity.setId(ADDRESS_ID);
        entity.setZip(ZIP);
        entity.setCity(CITY);
        return entity;
    }

}
