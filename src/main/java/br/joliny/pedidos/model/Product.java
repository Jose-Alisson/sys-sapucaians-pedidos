package br.joliny.pedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "produtos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl;

    private String name;

    private String description;

    private String category;

    private double price;

    private boolean visible;

    private boolean available;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AdditionalManager> additional;
}
