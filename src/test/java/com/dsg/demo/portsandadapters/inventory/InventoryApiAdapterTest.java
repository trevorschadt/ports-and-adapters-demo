package com.dsg.demo.portsandadapters.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RestClientTest
class InventoryApiAdapterTest {

    @Spy
    private RestTemplate restTemplate;

    private InventoryApiAdapter testSubject;

    private final UUID itemId = UUID.randomUUID();
    private final Random randomizer = new Random();


    @BeforeEach
    void setUp() {
        testSubject = new InventoryApiAdapter(restTemplate);
    }

    @Test
    void getInventory_shouldReturnNullWhenRestTemplateThrowsARestClientException() {
        doThrow(RestClientException.class)
                .when(restTemplate)
                .getForEntity(anyString(), eq(InventoryResponseDTO.class), any(UUID.class));
        assertThat(testSubject.getInventory(itemId)).isNull();
        verify(restTemplate, times(1)).getForEntity("https://inventory.otherteam.dcsg.com/api/v1/items/{itemId}/inventory", InventoryResponseDTO.class, itemId);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void getInventory_shouldReturnAnEmptyMapWhenRestTemplateReturnsAnEmptyBody() {
        doReturn(ResponseEntity.ok(InventoryResponseDTO.builder().build()))
                .when(restTemplate)
                .getForEntity(anyString(), eq(InventoryResponseDTO.class), any(UUID.class));
        assertThat(testSubject.getInventory(itemId)).isEmpty();
        verify(restTemplate, times(1)).getForEntity("https://inventory.otherteam.dcsg.com/api/v1/items/{itemId}/inventory", InventoryResponseDTO.class, itemId);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void getInventory_shouldReturnMappedEntries() {
        int store1Inventory = randomizer.nextInt(100);
        int store2Inventory = randomizer.nextInt(100);
        int store3Inventory = randomizer.nextInt(100);
        List<InventoryDTO> entities = List.of(
                InventoryDTO.builder().storeNumber("store-1").currentInventory(store1Inventory).build(),
                InventoryDTO.builder().storeNumber("store-2").currentInventory(store2Inventory).build(),
                InventoryDTO.builder().storeNumber("store-3").currentInventory(store3Inventory).build()
        );
        doReturn(ResponseEntity.ok(InventoryResponseDTO.builder().items(entities).build()))
                .when(restTemplate)
                .getForEntity(anyString(), eq(InventoryResponseDTO.class), any(UUID.class));
        assertThat(testSubject.getInventory(itemId))
                .containsExactly(
                        entry("store-1", store1Inventory),
                        entry("store-2", store2Inventory),
                        entry("store-3", store3Inventory)
                );
        verify(restTemplate, times(1)).getForEntity("https://inventory.otherteam.dcsg.com/api/v1/items/{itemId}/inventory", InventoryResponseDTO.class, itemId);
        verifyNoMoreInteractions(restTemplate);
    }

}