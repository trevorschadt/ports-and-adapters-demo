package com.dsg.demo.portsandadapters.controllers;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode
class InventoryItemDTO {
    String storeNumber;
    Integer currentInventory;
}
