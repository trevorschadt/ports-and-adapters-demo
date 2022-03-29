package com.dsg.demo.portsandadapters.domain.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Map;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Item {
    UUID id;
    String name;
    String internalName;
    Map<String, Integer> storeInventory;
}
