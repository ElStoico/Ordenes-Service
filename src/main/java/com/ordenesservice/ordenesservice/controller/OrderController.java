package com.ordenesservice.ordenesservice.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ordenesservice.ordenesservice.dto.UpdateOrderStatusRequest;
import com.ordenesservice.ordenesservice.model.Order;
import com.ordenesservice.ordenesservice.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ordenes")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order) {
        log.info("Creando orden usuarioId={} productoId={} cantidad={}",
                order.getUsuarioId(), order.getProductoId(), order.getCantidad());
        try {
            return orderService.createOrder(order);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        try {
            return orderService.getOrderById(id);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping("/usuario/{id}")
    public List<Order> getOrdersByUsuario(@PathVariable("id") String usuarioId) {
        return orderService.getOrdersByUsuarioId(usuarioId);
    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable String id, @RequestBody UpdateOrderStatusRequest request) {
        try {
            return orderService.updateOrderStatus(id, request.getEstado());
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
