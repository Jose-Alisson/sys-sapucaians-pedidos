package br.joliny.pedidos.repository;

import br.joliny.pedidos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from produtos where lower(name) like lower(concat('%', ?1, '%')) and visible >= ?2", nativeQuery = true)
    List<Product> search(String s, boolean visible);
}
