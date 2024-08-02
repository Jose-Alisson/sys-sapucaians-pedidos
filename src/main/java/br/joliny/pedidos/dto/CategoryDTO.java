package br.joliny.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDTO {

    private String name;
    private List<ProductDTO> products;
}
