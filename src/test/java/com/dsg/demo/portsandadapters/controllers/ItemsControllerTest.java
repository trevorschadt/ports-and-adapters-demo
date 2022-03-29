package com.dsg.demo.portsandadapters.controllers;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.models.Item;
import com.dsg.demo.portsandadapters.domain.ports.inbound.ItemRetriever;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemsControllerTest {

    @Mock
    private ItemRetriever itemRetriever;

    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    private ItemsController testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new ItemsController(itemRetriever, itemMapper);
    }

    @Test
    void getItemById_shouldReturnNotFoundResponseIfPortReturnsNull() throws ItemNotFoundException {
        UUID itemId = UUID.randomUUID();
        doThrow(ItemNotFoundException.class)
                .when(itemRetriever)
                .getItem(any());
        ResponseEntity<ItemDTO> responseEntity = testSubject.getItemById(itemId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(testSubject, times(1)).getItemById(itemId);
        verifyNoMoreInteractions(testSubject);
    }

    @Test
    void getItemById_shouldReturnInternalServerErrorResponseIfPortThrowsOtherError() throws ItemNotFoundException {
        UUID itemId = UUID.randomUUID();
        doThrow(RuntimeException.class)
                .when(itemRetriever)
                .getItem(any());
        ResponseEntity<ItemDTO> responseEntity = testSubject.getItemById(itemId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        verify(testSubject, times(1)).getItemById(itemId);
        verifyNoMoreInteractions(testSubject);
    }

    @Test
    void getItemById_shouldReturnMappedPortResponse() throws ItemNotFoundException {
        Item item = Item.builder().id(UUID.randomUUID()).name("item-name").build();
        UUID itemId = UUID.randomUUID();
        doReturn(item)
                .when(itemRetriever)
                .getItem(any());
        ResponseEntity<ItemDTO> responseEntity = testSubject.getItemById(itemId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(itemMapper.toResponse(item));
        verify(testSubject, times(1)).getItemById(itemId);
        verifyNoMoreInteractions(testSubject);
    }
}