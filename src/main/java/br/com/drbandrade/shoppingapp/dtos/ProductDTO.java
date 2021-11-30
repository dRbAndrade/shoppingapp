package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private int ordered;
    private double price;
    private int available;

    public ProductDTO() {
    }

    public ProductDTO(Long id) {
        this.id = id;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.available = entity.getAvailable();
        this.ordered = entity.getOrdered();
    }

}
