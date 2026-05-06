package com.ordenesservice.ordenesservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.ordenesservice.ordenesservice.dto.InventoryUpdateEvent;
import com.ordenesservice.ordenesservice.model.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventProducer {

    private final KafkaTemplate<String, InventoryUpdateEvent> kafkaTemplate;

    @Value("${app.topic.inventory-update}")
    private String inventoryUpdateTopic;

    public void publishOrderCreated(Order order) {
        log.info("[PRODUCER] ===== START: Publishing inventory event for order: {} =====", order.getId());
        log.debug("[PRODUCER] Order details - ProductoId: {}, Cantidad: {}", order.getProductoId(), order.getCantidad());
        
        InventoryUpdateEvent event = InventoryUpdateEvent.builder()
                .orderId(order.getId())
                .productId(order.getProductoId())
                .quantity(order.getCantidad())
                .build();

        log.info("[PRODUCER] Event created: {}", event);
        log.info("[PRODUCER] Topic: {}", inventoryUpdateTopic);

        try {
            log.info("[PRODUCER] Sending message to Kafka...");
            var sendResult = kafkaTemplate.send(inventoryUpdateTopic, order.getId(), event);
            var completableFuture = sendResult.toCompletableFuture();
            
            completableFuture.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("[PRODUCER] ✓ Message sent successfully! Partition: {}, Offset: {}", 
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                } else {
                    log.error("[PRODUCER] ✗ Failed to send message: {}", ex.getMessage(), ex);
                }
            });
            
            log.info("[PRODUCER] ===== END: Inventory event publishing initiated =====");
        } catch (Exception ex) {
            log.error("[PRODUCER] Exception during send: {}", ex.getMessage(), ex);
            throw new IllegalStateException("No se pudo publicar el evento de inventario para la orden "
                    + order.getId(), ex);
        }
    }
}