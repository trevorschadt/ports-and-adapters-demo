package com.dsg.demo.portsandadapters.domain.adapters;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.models.Item;
import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundInventoryRetriever;
import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundItemRetriever;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemAdapterTest {

    @Mock
    private OutboundItemRetriever outboundItemRetriever;
    @Mock
    private OutboundInventoryRetriever outboundInventoryRetriever;

    private ItemAdapter testSubject;
    private final Random randomizer = new Random();

    @BeforeEach
    void setUp() {
        testSubject = new ItemAdapter(outboundItemRetriever, outboundInventoryRetriever);
    }

    @Test
    void getItem_shouldBubbleItemNotFoundExceptionFromPort() throws ItemNotFoundException {
        doThrow(ItemNotFoundException.class)
                .when(outboundItemRetriever)
                .getItem(any());
        UUID itemId = UUID.randomUUID();
        assertThatThrownBy(() -> testSubject.getItem(itemId))
                .isExactlyInstanceOf(ItemNotFoundException.class);
        verify(outboundItemRetriever, times(1)).getItem(itemId);
        verifyNoMoreInteractions(outboundItemRetriever);
        verifyNoInteractions(outboundInventoryRetriever);
    }

    @Test
    void getItem_shouldReturnCombinedResultsFromPorts() throws ItemNotFoundException {
        UUID itemId = UUID.randomUUID();
        Item item = Item.builder().id(itemId).name("item-name").internalName("internal-item-name").build();
        doReturn(item)
                .when(outboundItemRetriever)
                .getItem(any());
        Map<String, Integer> inventory = Map.ofEntries(
                Map.entry("store-1", randomizer.nextInt(100)),
                Map.entry("store-2", randomizer.nextInt(100)),
                Map.entry("store-3", randomizer.nextInt(100))
        );
        doReturn(inventory)
                .when(outboundInventoryRetriever)
                .getInventory(any());
        Item result = testSubject.getItem(itemId);
        assertThat(result).extracting("id", "name", "internalName")
                .containsExactly(itemId, "item-name", "internal-item-name");
        assertThat(result.getStoreInventory())
                .containsExactlyInAnyOrderEntriesOf(inventory);
        verify(outboundItemRetriever, times(1)).getItem(itemId);
        verify(outboundInventoryRetriever, times(1)).getInventory(itemId);
        verifyNoMoreInteractions(outboundItemRetriever, outboundInventoryRetriever);
    }
}