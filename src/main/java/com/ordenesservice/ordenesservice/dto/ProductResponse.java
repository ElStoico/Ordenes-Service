package com.ordenesservice.ordenesservice.dto;

import lombok.Data;

@Data
public class ProductResponse {

    private String id;
    private String nombre;
    private String descripcion;
    private Double precio;
}
