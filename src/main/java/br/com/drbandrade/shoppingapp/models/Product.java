package br.com.drbandrade.shoppingapp.models;

import br.com.drbandrade.shoppingapp.dtos.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int ordered;
    private double price;
    private int available;
    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orders;

    public Product() {
    }

    public Product(ProductDTO dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.available = dto.getAvailable();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ordered=" + ordered +
                ", price=" + price +
                ", available=" + available +
                ", orders=" + orders +
                '}';
    }
}
