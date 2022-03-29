package com.dsg.demo.portsandadapters.repository;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
class ItemEntity {
    @Id
    UUID id = UUID.randomUUID();

    String name;

    String internalName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ItemEntity entity = (ItemEntity) o;
        return id != null && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
