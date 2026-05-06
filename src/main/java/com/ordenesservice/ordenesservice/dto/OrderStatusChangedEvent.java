package com.ordenesservice.ordenesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusChangedEvent {
    private String orderId;
    private String usuarioId;
    private String productId;
    private Double totalPrice;
    private String newStatus;
    private String previousStatus;
    private Long timestamp;
}
