package com.ordenesservice.ordenesservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ordenes")
public class Order {

    @Id
    private String id;
    private String usuarioId;
    private String productoId;
    private Integer cantidad;
    private Double precioTotal;
    private String estado;
}
