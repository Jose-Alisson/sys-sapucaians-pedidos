package br.joliny.pedidos.repository;

import br.joliny.pedidos.model.Amount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountRepository extends JpaRepository<Amount, Long> {
}
