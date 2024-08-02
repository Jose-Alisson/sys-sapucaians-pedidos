package br.joliny.pedidos.dto;

import br.joliny.pedidos.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {


    private Long id;

    private LocalDateTime dateCreation;

    private String name;

    private String cellPhone;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<AmountDTO> amounts;

    private String payment;

    private String address;

    private OrderStatus status;
}
