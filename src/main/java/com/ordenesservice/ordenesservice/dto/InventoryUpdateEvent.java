package com.ordenesservice.ordenesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateEvent {

    private String orderId;
    private String productId;
    private Integer quantity;
}