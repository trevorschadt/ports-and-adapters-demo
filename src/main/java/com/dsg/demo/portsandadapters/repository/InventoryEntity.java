package com.dsg.demo.portsandadapters.repository;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.UUID;

@Entity
@IdClass(InventoryEntity.InventoryEntityId.class)
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
class InventoryEntity {

    @Id
    UUID itemId = UUID.randomUUID();
    @Id
    String storeNumber = null;

    Integer currentInventory = null;

    @Value
    @Builder
    static class InventoryEntityId implements Serializable {
        UUID itemId;
        String storeNumber;
    }
}
