package com.ordenesservice.ordenesservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ordenesservice.ordenesservice.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUsuarioId(String usuarioId);
}
