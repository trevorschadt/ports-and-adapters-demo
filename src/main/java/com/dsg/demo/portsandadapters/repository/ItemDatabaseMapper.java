package com.dsg.demo.portsandadapters.repository;

import com.dsg.demo.portsandadapters.domain.models.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ItemDatabaseMapper {
    Item toDomain(ItemEntity source);
}
