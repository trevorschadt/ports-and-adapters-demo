package com.dsg.demo.portsandadapters.domain.ports.inbound;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.models.Item;

import java.util.UUID;

public interface ItemRetriever {
    Item getItem(UUID itemId) throws ItemNotFoundException;
}
