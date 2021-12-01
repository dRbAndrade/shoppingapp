package br.com.drbandrade.shoppingapp.services;

import br.com.drbandrade.shoppingapp.dtos.ProductDTO;
import br.com.drbandrade.shoppingapp.models.Product;
import br.com.drbandrade.shoppingapp.repositories.ProductRepository;
import br.com.drbandrade.shoppingapp.services.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProductDTO persistNew(ProductDTO dto){
        Product entity = repository.save(new Product(dto));
        return new ProductDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findPaged(Pageable pageable){
        return repository.findAll(pageable).map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(long id){
        Product entity = repository.findById(id).orElseThrow(
                ()->new ProductNotFoundException(String.format("No product with id: %d was found",id))
        );
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(long id,ProductDTO dto){
        Product entity = repository.getById(id);
        entity.setName(dto.getName());
        entity.setAvailable(dto.getAvailable());
        entity.setPrice(dto.getPrice());
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }
    @Transactional
    public void delete(long id){
        repository.deleteById(id);

    }

}
