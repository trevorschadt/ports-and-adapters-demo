package com.dsg.demo.portsandadapters.domain.ports.outbound;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.models.Item;

import java.util.UUID;

public interface OutboundItemRetriever {
    Item getItem(UUID itemId) throws ItemNotFoundException;
}
