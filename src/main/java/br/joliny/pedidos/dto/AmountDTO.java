package br.joliny.pedidos.dto;

import br.joliny.pedidos.model.Additional;
import br.joliny.pedidos.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class AmountDTO {

    private Product product;

    private int quantity;

    private String description;

    private List<Additional> additional;
}
