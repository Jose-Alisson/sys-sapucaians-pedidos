package br.joliny.pedidos.repository;

import br.joliny.pedidos.model.Order;
import org.springframework.beans.factory.annotation.Value;
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

    @Query(value = "select o.* from orders o inner join orders_amounts oa inner join amounts a where oa.order_id = o.id and oa.amounts_id = a.id and a.product_id = ?1", nativeQuery= true)
    List<Order> findAllByProductIndex(Long productId);
}
