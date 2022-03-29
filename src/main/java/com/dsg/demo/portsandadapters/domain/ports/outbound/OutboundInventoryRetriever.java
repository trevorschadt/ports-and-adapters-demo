package com.dsg.demo.portsandadapters.domain.ports.outbound;

import java.util.Map;
import java.util.UUID;

public interface OutboundInventoryRetriever {
    Map<String, Integer> getInventory(UUID itemId);
}
