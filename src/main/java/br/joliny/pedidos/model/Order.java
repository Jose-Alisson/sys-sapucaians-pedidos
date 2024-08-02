package br.joliny.pedidos.model;

import br.joliny.pedidos.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "orders")
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    private String name;

    @Column(name = "cell_phone")
    private String cellPhone;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Amount> amounts;

    private String payment;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
