package com.dsg.demo.portsandadapters.domain.adapters;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.models.Item;
import com.dsg.demo.portsandadapters.domain.ports.inbound.ItemRetriever;
import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundInventoryRetriever;
import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundItemRetriever;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
