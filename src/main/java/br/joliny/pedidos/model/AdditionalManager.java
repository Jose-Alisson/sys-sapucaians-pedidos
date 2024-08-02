package br.joliny.pedidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Table(name = "additional_managers")
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalManager implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int max;

    private int min;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Additional> additional;
}
