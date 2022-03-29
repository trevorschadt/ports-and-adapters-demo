package com.dsg.demo.portsandadapters.repository;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemDatabaseAdapterTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private InventoryRepository inventoryRepository;

    private final ItemDatabaseMapper itemDatabaseMapper = Mappers.getMapper(ItemDatabaseMapper.class);

    private ItemDatabaseAdapter testSubject;

    private final UUID itemId = UUID.randomUUID();

    private final Random randomizer = new Random();

    @BeforeEach
    void setUp() {
        testSubject = new ItemDatabaseAdapter(itemRepository, inventoryRepository, itemDatabaseMapper);
    }

    @Test
    void getItem_shouldThrowExceptionIfItemIsNotFound() {
        doReturn(Optional.empty())
                .when(itemRepository)
                .findById(any());
        assertThatThrownBy(() -> testSubject.getItem(itemId))
                .isExactlyInstanceOf(ItemNotFoundException.class);
        verify(itemRepository, times(1)).findById(itemId);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void getItem_shouldReturnMappedEntityIfItemIsFound() throws ItemNotFoundException {
        ItemEntity entity = ItemEntity.builder()
                .id(UUID.randomUUID())
                .name("item-name")
                .internalName("item-internal-name")
                .build();
        doReturn(Optional.of(entity))
                .when(itemRepository)
                .findById(any());
        assertThat(testSubject.getItem(itemId))
                .isEqualTo(itemDatabaseMapper.toDomain(entity));
        verify(itemRepository, times(1)).findById(itemId);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void getInventory_shouldReturnEmptyMapFromEmptyList() {
        doReturn(Collections.emptyList())
                .when(inventoryRepository)
                .findAllByItemId(any());
        assertThat(testSubject.getInventory(itemId)).isEmpty();
        verify(inventoryRepository, times(1)).findAllByItemId(itemId);
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    void getInventory_shouldReturnMappedEntries() {
        int store1Inventory = randomizer.nextInt(100);
        int store2Inventory = randomizer.nextInt(100);
        int store3Inventory = randomizer.nextInt(100);
        List<InventoryEntity> entities = List.of(
                InventoryEntity.builder().itemId(itemId).storeNumber("store-1").currentInventory(store1Inventory).build(),
                InventoryEntity.builder().itemId(itemId).storeNumber("store-2").currentInventory(store2Inventory).build(),
                InventoryEntity.builder().itemId(itemId).storeNumber("store-3").currentInventory(store3Inventory).build()
        );
        doReturn(entities)
                .when(inventoryRepository)
                .findAllByItemId(any());
        assertThat(testSubject.getInventory(itemId))
                .containsExactly(
                        entry("store-1", store1Inventory),
                        entry("store-2", store2Inventory),
                        entry("store-3", store3Inventory)
                );
        verify(inventoryRepository, times(1)).findAllByItemId(itemId);
        verifyNoMoreInteractions(itemRepository);
    }
}