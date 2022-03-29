package com.dsg.demo.portsandadapters.repository;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.models.Item;
import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundItemRetriever;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
class ItemDatabaseAdapter implements OutboundItemRetriever /*, OutboundInventoryRetriever */ {
    private final ItemRepository itemRepository;
    //    private final InventoryRepository inventoryRepository;
    private final ItemDatabaseMapper itemDatabaseMapper;

    ItemDatabaseAdapter(ItemRepository itemRepository, /* InventoryRepository inventoryRepository, */ ItemDatabaseMapper itemDatabaseMapper) {
        this.itemRepository = itemRepository;
//        this.inventoryRepository = inventoryRepository;
        this.itemDatabaseMapper = itemDatabaseMapper;
    }

    @Override
    public Item getItem(UUID itemId) throws ItemNotFoundException {
        Optional<ItemEntity> optionalEntity = itemRepository.findById(itemId);
        if (optionalEntity.isPresent()) return itemDatabaseMapper.toDomain(optionalEntity.get());
        throw new ItemNotFoundException();
    }
//
//    @Override
//    public Map<String, Integer> getInventory(UUID itemId) {
//        return inventoryRepository.findAllByItemId(itemId)
//                .stream()
//                .collect(Collectors.toMap(
//                        InventoryEntity::getStoreNumber,
//                        InventoryEntity::getCurrentInventory)
//                );
//    }
}
