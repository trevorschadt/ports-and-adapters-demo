package com.dsg.demo.portsandadapters.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class InventoryDTO {
    @JsonProperty("store")
    String storeNumber;
    @JsonProperty("inventory")
    Integer currentInventory;
}
