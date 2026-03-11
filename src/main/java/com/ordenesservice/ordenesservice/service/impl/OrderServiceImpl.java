package com.ordenesservice.ordenesservice.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ordenesservice.ordenesservice.client.ProductClient;
import com.ordenesservice.ordenesservice.dto.ProductResponse;
import com.ordenesservice.ordenesservice.model.Order;
import com.ordenesservice.ordenesservice.repository.OrderRepository;
import com.ordenesservice.ordenesservice.service.OrderService;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String ESTADO_INICIAL = "CREADA";

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    public Order createOrder(Order order) {
        ProductResponse productResponse;

        try {
            productResponse = productClient.getProductById(order.getProductoId());
        } catch (FeignException.NotFound ex) {
            throw new NoSuchElementException("Producto no encontrado con id: " + order.getProductoId());
        }

        order.setId(null);
        if (order.getEstado() == null || order.getEstado().isBlank()) {
            order.setEstado(ESTADO_INICIAL);
        }

        Double precioUnitario = productResponse.getPrecio() == null ? 0D : productResponse.getPrecio();
        Integer cantidad = order.getCantidad() == null ? 0 : order.getCantidad();
        order.setPrecioTotal(precioUnitario * cantidad);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Orden no encontrada con id: " + id));
    }

    @Override
    public List<Order> getOrdersByUsuarioId(String usuarioId) {
        return orderRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Order updateOrderStatus(String id, String estado) {
        Order existingOrder = getOrderById(id);
        existingOrder.setEstado(estado);
        return orderRepository.save(existingOrder);
    }
}
