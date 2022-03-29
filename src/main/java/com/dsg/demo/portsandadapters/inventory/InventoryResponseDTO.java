package com.dsg.demo.portsandadapters.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class InventoryResponseDTO {
    @JsonProperty
    @Singular
    List<InventoryDTO> items;
}
