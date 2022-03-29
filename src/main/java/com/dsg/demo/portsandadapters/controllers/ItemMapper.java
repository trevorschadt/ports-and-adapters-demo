package com.dsg.demo.portsandadapters.controllers;

import com.dsg.demo.portsandadapters.domain.models.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ItemMapper {
    ItemDTO toResponse(Item item);
}
