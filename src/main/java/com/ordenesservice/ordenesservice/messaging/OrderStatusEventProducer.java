package com.ordenesservice.ordenesservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.ordenesservice.ordenesservice.dto.OrderStatusChangedEvent;
import com.ordenesservice.ordenesservice.model.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusEventProducer {

    private final KafkaTemplate<String, OrderStatusChangedEvent> kafkaTemplate;

    @Value("${app.topic.order-status-changed}")
    private String orderStatusTopic;

    public void publishOrderStatusChanged(Order order, String previousStatus) {
        log.info("[PRODUCER-STATUS] ===== START: Publishing order status changed event for order: {} =====", 
                order.getId());
        log.debug("[PRODUCER-STATUS] Order details - Status: {} -> {}", previousStatus, order.getEstado());
        
        OrderStatusChangedEvent event = OrderStatusChangedEvent.builder()
                .orderId(order.getId())
                .usuarioId(order.getUsuarioId())
                .productId(order.getProductoId())
                .totalPrice(order.getPrecioTotal())
                .newStatus(order.getEstado())
                .previousStatus(previousStatus)
                .timestamp(System.currentTimeMillis())
                .build();

        log.info("[PRODUCER-STATUS] Event created: {}", event);
        log.info("[PRODUCER-STATUS] Topic: {}", orderStatusTopic);

        try {
            log.info("[PRODUCER-STATUS] Sending message to Kafka...");
            var sendResult = kafkaTemplate.send(orderStatusTopic, order.getId(), event);
            var completableFuture = sendResult.toCompletableFuture();
            
            completableFuture.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("[PRODUCER-STATUS] ✓ Message sent successfully! Partition: {}, Offset: {}", 
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                } else {
                    log.error("[PRODUCER-STATUS] ✗ Failed to send message: {}", ex.getMessage(), ex);
                }
            });
            
            log.info("[PRODUCER-STATUS] ===== END: Order status event publishing initiated =====");
        } catch (Exception ex) {
            log.error("[PRODUCER-STATUS] Exception during send: {}", ex.getMessage(), ex);
            throw new IllegalStateException("No se pudo publicar el evento de cambio de estado para la orden "
                    + order.getId(), ex);
        }
    }
}
