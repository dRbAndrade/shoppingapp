package br.com.drbandrade.shoppingapp.controllers;

import br.com.drbandrade.shoppingapp.dtos.ProductDTO;
import br.com.drbandrade.shoppingapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;
    @GetMapping("/inventory")
    public ResponseEntity<Page<ProductDTO>> findPaged(Pageable pageable){
        Page<ProductDTO> response = service.findPaged(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> persistNew(@RequestBody ProductDTO dto){
        ProductDTO response = service.persistNew(dto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto){
        ProductDTO response = service.update(id,dto);
        return ResponseEntity.ok(response);
    }

}
