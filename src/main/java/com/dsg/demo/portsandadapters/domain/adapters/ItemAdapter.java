package com.dsg.demo.portsandadapters.domain.adapters;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.models.Item;
import com.dsg.demo.portsandadapters.domain.ports.inbound.ItemRetriever;
import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundInventoryRetriever;
import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundItemRetriever;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Notice that this class did not have to change when we switched out the implementation behind the interface!
 * All the class needs to worry about is "push button, get data." It doesn't need to worry about how the data is gotten.
 */

@Component
class ItemAdapter implements ItemRetriever {
    private final OutboundItemRetriever outboundItemRetriever;
    private final OutboundInventoryRetriever outboundInventoryRetriever;

    public ItemAdapter(OutboundItemRetriever outboundItemRetriever, OutboundInventoryRetriever outboundInventoryRetriever) {
        this.outboundItemRetriever = outboundItemRetriever;
        this.outboundInventoryRetriever = outboundInventoryRetriever;
    }

    @Override
    public Item getItem(UUID itemId) throws ItemNotFoundException {
        return outboundItemRetriever.getItem(itemId)
                .toBuilder()
                .storeInventory(outboundInventoryRetriever.getInventory(itemId))
                .build();
    }

}
