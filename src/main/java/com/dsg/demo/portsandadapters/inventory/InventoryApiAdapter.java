package com.dsg.demo.portsandadapters.inventory;

import com.dsg.demo.portsandadapters.domain.ports.outbound.OutboundInventoryRetriever;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This class (and its support classes) are the only new code we need to write (and therefore test)
 */
@Component
public class InventoryApiAdapter implements OutboundInventoryRetriever {
    private final Logger logger = getLogger(this.getClass());

    private final RestTemplate restTemplate;

    public InventoryApiAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Integer> getInventory(UUID itemId) {
        try {
            ResponseEntity<InventoryResponseDTO> response = restTemplate.getForEntity("https://inventory.otherteam.dcsg.com/api/v1/items/{itemId}/inventory", InventoryResponseDTO.class, itemId);
            InventoryResponseDTO responseBody = response.getBody();
            return responseBody == null
                    ? null
                    : responseBody.getItems().stream().collect(Collectors.toMap(InventoryDTO::getStoreNumber, InventoryDTO::getCurrentInventory));
        } catch (RestClientException ex) {
            logger.error("Whoopsie");
            return null;
        }
    }
}
