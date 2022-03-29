package com.dsg.demo.portsandadapters.controllers;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@EqualsAndHashCode
class ItemDTO {
    UUID id;
    String name;
    List<InventoryItemDTO> inventory;
}
