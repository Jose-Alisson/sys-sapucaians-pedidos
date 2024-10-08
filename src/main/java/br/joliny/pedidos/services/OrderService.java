package br.joliny.pedidos.services;

import br.joliny.pedidos.dto.OrderDTO;
import br.joliny.pedidos.enums.OrderStatus;
import br.joliny.pedidos.exception.causable.ErrDataTransfer;
import br.joliny.pedidos.model.AdditionalManager;
import br.joliny.pedidos.model.Amount;
import br.joliny.pedidos.model.Order;
import br.joliny.pedidos.model.Product;
import br.joliny.pedidos.repository.AmountRepository;
import br.joliny.pedidos.repository.OrderRepository;
import br.joliny.pedidos.services.impl.MethodsCRUD;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService implements MethodsCRUD<OrderDTO, Order> {

    @Value("${api.main.url}")
    private String API_MAIN;

    private final WebClient client = WebClient.create();

    @Autowired
    private OrderRepository repository;

    @Autowired
    private AmountRepository amountRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public OrderDTO create(Order o) {
        o.setStatus(OrderStatus.CREATED);
        o.setDateCreation(LocalDateTime.now(TimeZone.getTimeZone("America/Sao_Paulo").toZoneId()));

        OrderDTO dto = mapper.map(repository.save(o), OrderDTO.class);
        String a = client.post().uri("%s/order/add".formatted(API_MAIN)).bodyValue(dto).retrieve().bodyToMono(String.class).block();
        return dto;
    }

    @Override
    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Optional<Order> pedido_ = repository.findById(id);

        if (pedido_.isPresent()) {
            Order pedido = Order.builder()
                    .id(id)
                    .dateCreation(pedido_.get().getDateCreation())
                    .name(orderDTO.getName())
                    .cellPhone(orderDTO.getCellPhone())
                    .amounts(orderDTO.getAmounts().stream().map(contadorDTO -> mapper.map(contadorDTO, Amount.class)).toList())
                    .payment(orderDTO.getPayment())
                    .address(orderDTO.getAddress())
                    .status(orderDTO.getStatus()).build();

            var dto = mapper.map(repository.save(pedido), OrderDTO.class);

            String a = client.post().uri("%s/order/add".formatted(API_MAIN)).bodyValue(dto).retrieve().bodyToMono(String.class).block();

            return dto;
        }

        throw new ErrDataTransfer("Pedido de ID: %d não encontrado!".formatted(id), HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(Long id) {
        Optional<Order> pedido_ = repository.findById(id);

        if (pedido_.isPresent()) {
            repository.deleteById(id);
            return;
        }

        throw new ErrDataTransfer("Pedido de ID: %d não encontrado!".formatted(id), HttpStatus.NOT_FOUND);
    }

    @Override
    public OrderDTO get(Long id) {
        Optional<Order> pedido_ = repository.findById(id);

        if (pedido_.isPresent()) {
            return mapper.map(pedido_.get(), OrderDTO.class);
        }

        throw new ErrDataTransfer("Pedido de ID: %d não encontrado!".formatted(id), HttpStatus.NOT_FOUND);
    }

    @Override
    public List<OrderDTO> getAll() {
        List<Order> pedidos_ = repository.findAll();

        if (pedidos_.isEmpty()) {
            throw new ErrDataTransfer("Lista de pedidos vazia", HttpStatus.NO_CONTENT);
        }
        return pedidos_.stream().map(pedido -> mapper.map(pedido, OrderDTO.class)).toList();
    }

    public void removeAmountsByProductId(Long id) {
        List<Order> orders = new ArrayList<>();

        repository.findAllByProductIndex(id).forEach(order -> {
            var amounts = order.getAmounts().stream().filter(amount -> amount.getProduct().getId() != id).toList();

            amountRepository.deleteAll(amounts);

            System.out.println(amounts);
            Order pedido = Order.builder()
                    .id(order.getId())
                    .dateCreation(order.getDateCreation())
                    .name(order.getName())
                    .cellPhone(order.getCellPhone())
                    .amounts(order.getAmounts().stream().filter(amount -> amount.getProduct().getId() != id).toList())
                    .payment(order.getPayment())
                    .address(order.getAddress())
                    .status(order.getStatus()).build();

            orders.add(pedido);
        });

        repository.saveAll(orders);
        String a = client.post().uri("%s/order/reload".formatted(API_MAIN)).retrieve().bodyToMono(String.class).block();
    }

    public List<Date> getDates() {
        return repository.getDates();
    }

    public List<OrderDTO> getByDates(String date) {
        return repository.getAllByDate(date).stream().map(order -> mapper.map(order, OrderDTO.class)).toList();
    }
}
