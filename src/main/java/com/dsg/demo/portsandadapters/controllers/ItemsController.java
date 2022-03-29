package com.dsg.demo.portsandadapters.controllers;

import com.dsg.demo.portsandadapters.domain.exceptions.ItemNotFoundException;
import com.dsg.demo.portsandadapters.domain.ports.inbound.ItemRetriever;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/v1/items")
class ItemsController {

    private final Logger logger = getLogger(this.getClass());

    private final ItemRetriever itemRetriever;
    private final ItemMapper itemMapper;

    ItemsController(ItemRetriever itemRetriever, ItemMapper itemMapper) {
        this.itemRetriever = itemRetriever;
        this.itemMapper = itemMapper;
    }

    @GetMapping("/{itemId}")
    ResponseEntity<ItemDTO> getItemById(@PathVariable UUID itemId) {
        try {
            return ResponseEntity.ok(itemMapper.toResponse(itemRetriever.getItem(itemId)));
        } catch (ItemNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Whoopsie");
            return ResponseEntity.internalServerError().build();
        }
    }
}
