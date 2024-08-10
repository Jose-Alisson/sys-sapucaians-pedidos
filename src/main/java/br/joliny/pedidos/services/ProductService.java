package br.joliny.pedidos.services;

import br.joliny.pedidos.dto.CategoryDTO;
import br.joliny.pedidos.dto.ProductDTO;
import br.joliny.pedidos.exception.causable.ErrDataTransfer;
import br.joliny.pedidos.model.AdditionalManager;
import br.joliny.pedidos.model.Product;
import br.joliny.pedidos.repository.ProductRepository;
import br.joliny.pedidos.services.impl.MethodsCRUD;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements MethodsCRUD<ProductDTO, Product> {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private OrderService orderService;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public ProductDTO create(Product o) {
        return mapper.map(repository.save(o), ProductDTO.class);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Optional<Product> produto_ = repository.findById(id);

        if(produto_.isPresent()){
            Product product = Product.builder()
                    .id(id)
                    .name(productDTO.getName())
                    .category(productDTO.getCategory())
                    .photoUrl(productDTO.getPhotoUrl())
                    .description(productDTO.getDescription())
                    .price(productDTO.getPrice())
                    .available(productDTO.isAvailable())
                    .visible(productDTO.isVisible())
                    .additional(productDTO.getAdditional().stream().map(manager -> mapper.map(manager, AdditionalManager.class)).toList())
                    .build();

            return mapper.map(repository.save(product), ProductDTO.class);
        }

        throw new ErrDataTransfer("Produto de ID: %d não encontrado!".formatted(id), HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(Long id) {
        Optional<Product> produto_ = repository.findById(id);

        if(produto_.isPresent()){
            repository.deleteById(id);
            return;
        }
        throw new ErrDataTransfer("Produto de ID: %d não encontrado!".formatted(id), HttpStatus.NOT_FOUND);
    }

    @Override
    public ProductDTO get(Long id) {
        Optional<Product> produto_ = repository.findById(id);

        if(produto_.isPresent()){
            return mapper.map(produto_.get(), ProductDTO.class);
        }

        throw new ErrDataTransfer("Produto de ID: %d não encontrado!".formatted(id), HttpStatus.NOT_FOUND);
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> produtos = repository.findAll();

        if(produtos.isEmpty()) {
            throw new ErrDataTransfer("Lista de produtos vazia!", HttpStatus.NO_CONTENT);
        } else {
            return produtos.stream().map(produto -> mapper.map(produto, ProductDTO.class)).toList();
        }
    }


    public List<CategoryDTO> getAllSortByCategory(){
        List<Product> produtos = repository.findAll();

        Map<String, List<Product>> produtosPorCategoria = produtos.stream()
                .filter(product -> product.getCategory() != null && product.isVisible())
                .collect(Collectors.groupingBy(Product::getCategory));

        // Criar a lista de CategoriaProduto
        List<CategoryDTO> listaDeCategoriaProdutos = produtosPorCategoria.entrySet()
                .stream()
                .map(entry -> new CategoryDTO(entry.getKey(),  entry.getValue().stream().map(produto  ->  mapper.map(produto, ProductDTO.class )).toList()))
                .collect(Collectors.toList());

        // Exibir os resultados

        return listaDeCategoriaProdutos;
    }

    public List<ProductDTO> search(String s) {
        return repository.search(s).stream().map(product -> mapper.map(product, ProductDTO.class)).toList();
    }
}
