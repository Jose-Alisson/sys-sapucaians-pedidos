package br.joliny.pedidos.repository;

import br.joliny.pedidos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query(value = "select distinct(date(date_creation)) as date_creation from orders", nativeQuery = true)
    List<Date> getDates();


    @Query(value = "select * from orders o where date(o.date_creation) = date(?1)",nativeQuery = true)
    List<Order> getAllByDate(String date);
}
