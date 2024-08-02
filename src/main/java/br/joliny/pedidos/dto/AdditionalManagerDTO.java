package br.joliny.pedidos.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdditionalManagerDTO {

    private Long id;

    private String name;

    private int max;

    private int min;

    private List<AdditionalDTO> additional;
}
