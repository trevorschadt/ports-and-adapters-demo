package com.dsg.demo.portsandadapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
interface InventoryRepository extends JpaRepository<InventoryEntity, InventoryEntity.InventoryEntityId> {
    List<InventoryEntity> findAllByItemId(UUID itemId);
}
