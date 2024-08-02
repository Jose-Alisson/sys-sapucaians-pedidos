package br.joliny.pedidos.controller;

import br.joliny.pedidos.dto.OrderDTO;
import br.joliny.pedidos.model.Order;
import br.joliny.pedidos.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class PedidoController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody Order pedido){
        return ResponseEntity.ok(service.create(pedido));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update (@PathVariable("id") Long id, @RequestBody OrderDTO dto){
        return  ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/dates")
    public ResponseEntity<?> getAllDates(){
        return ResponseEntity.ok(service.getDates());
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> getAllByDate(@RequestParam("date") String date){
        return ResponseEntity.ok(service.getByDates(date));
    }
}
