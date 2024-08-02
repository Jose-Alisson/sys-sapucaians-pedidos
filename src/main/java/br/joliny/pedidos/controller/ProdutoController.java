package br.joliny.pedidos.controller;

import br.joliny.pedidos.dto.ProductDTO;
import br.joliny.pedidos.model.Product;
import br.joliny.pedidos.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProdutoController {

    @Autowired
    private ProductService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product){
        return ResponseEntity.ok(service.create(product));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody ProductDTO produto){
        return ResponseEntity.ok(service.update(id, produto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/sortByCategory")
    public  ResponseEntity<?> getAllToCategory(){
        return ResponseEntity.ok(service.getAllSortByCategory());
    }

    @GetMapping("/search")
    public ResponseEntity<?> search (@RequestParam("s") String s){
        return ResponseEntity.ok(service.search(s));
    }
}
