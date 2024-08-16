package br.joliny.pedidos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductDTO {


    private Long id;

    private String photoUrl;

    private String name;

    private String description;

    private double price;

    private String category;

    private boolean available;

    private boolean visible;

    private List<AdditionalManagerDTO> additional;

    private String establishment;
}
