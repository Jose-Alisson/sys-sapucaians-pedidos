package br.joliny.pedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Table(name = "amounts")
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Amount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private String observation;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Additional> additional;
}
