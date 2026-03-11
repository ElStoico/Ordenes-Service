package com.ordenesservice.ordenesservice.service;

import java.util.List;

import com.ordenesservice.ordenesservice.model.Order;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrderById(String id);

    List<Order> getOrdersByUsuarioId(String usuarioId);

    Order updateOrderStatus(String id, String estado);
}
