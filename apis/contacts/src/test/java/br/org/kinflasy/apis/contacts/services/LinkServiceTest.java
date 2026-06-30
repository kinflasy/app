package br.org.kinflasy.apis.contacts.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import br.org.kinflasy.apis.contacts.entities.Link;
import br.org.kinflasy.apis.contacts.repositories.LinkRepository;
import br.org.kinflasy.libs.contacts.dto.LinkRequest;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    private static final UUID LINK_ID = UUID.fromString("a9d9696d-2d93-4331-ac73-ff37eff2eaaa");
    private static final String LABEL = "Site";
    private static final String URL = "https://kinflasy.org.br";

    @Mock
    private LinkRepository repository;

    private LinkService service;

    @BeforeEach
    void setUp() {
        service = new LinkService(new ModelMapper(), repository);
    }

    @Test
    void shouldCreateLink() {
        final var request = linkRequest();

        when(repository.save(any(Link.class))).thenAnswer(invocation -> {
            final Link entity = invocation.getArgument(0);
            entity.setId(LINK_ID);
            return entity;
        });

        final var result = service.create(request);

        final var entityCaptor = ArgumentCaptor.forClass(Link.class);
        verify(repository).save(entityCaptor.capture());

        assertAll(
                () -> assertEquals(LINK_ID, result.getId()),
                () -> assertEquals(LABEL, result.getLabel()),
                () -> assertEquals(URL, result.getUrl()),
                () -> assertEquals(LABEL, entityCaptor.getValue().getLabel()),
                () -> assertEquals(URL, entityCaptor.getValue().getUrl()));
    }

    @Test
    void shouldFindLinkById() {
        final var entity = link();

        when(repository.findById(LINK_ID)).thenReturn(Optional.of(entity));

        final var result = service.findById(LINK_ID);

        assertTrue(result.isPresent());
        assertAll(
                () -> assertEquals(LINK_ID, result.orElseThrow().getId()),
                () -> assertEquals(LABEL, result.orElseThrow().getLabel()),
                () -> assertEquals(URL, result.orElseThrow().getUrl()));
        verify(repository).findById(LINK_ID);
    }

    @Test
    void shouldReturnEmptyWhenLinkIsNotFoundById() {
        when(repository.findById(LINK_ID)).thenReturn(Optional.empty());

        final var result = service.findById(LINK_ID);

        assertTrue(result.isEmpty());
        verify(repository).findById(LINK_ID);
    }

    @Test
    void shouldUpdateLink() {
        final var request = linkRequest();
        final var original = new Link();
        original.setId(LINK_ID);

        when(repository.findById(LINK_ID)).thenReturn(Optional.of(original));
        when(repository.save(original)).thenReturn(original);

        final var result = service.update(LINK_ID, request);

        assertTrue(result.isPresent());
        assertAll(
                () -> assertEquals(LINK_ID, result.orElseThrow().getId()),
                () -> assertEquals(LABEL, result.orElseThrow().getLabel()),
                () -> assertEquals(URL, result.orElseThrow().getUrl()));

        final var order = inOrder(repository);
        order.verify(repository).findById(LINK_ID);
        order.verify(repository).save(original);
    }

    @Test
    void shouldReturnEmptyWhenLinkToUpdateIsNotFound() {
        final var request = linkRequest();

        when(repository.findById(LINK_ID)).thenReturn(Optional.empty());

        final var result = service.update(LINK_ID, request);

        assertTrue(result.isEmpty());
        verify(repository).findById(LINK_ID);
    }

    @Test
    void shouldDeleteLink() {
        doNothing().when(repository).deleteById(LINK_ID);

        service.delete(LINK_ID);

        verify(repository).deleteById(LINK_ID);
    }

    private LinkRequest linkRequest() {
        final var request = new LinkRequest();
        request.setLabel(LABEL);
        request.setUrl(URL);
        return request;
    }

    private Link link() {
        final var entity = new Link();
        entity.setId(LINK_ID);
        entity.setLabel(LABEL);
        entity.setUrl(URL);
        return entity;
    }

}
