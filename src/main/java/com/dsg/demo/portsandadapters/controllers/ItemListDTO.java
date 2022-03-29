package com.dsg.demo.portsandadapters.controllers;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@Builder
@EqualsAndHashCode
class ItemListDTO {
    List<ItemDTO> things;
}
