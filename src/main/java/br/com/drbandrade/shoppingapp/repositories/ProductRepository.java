package br.com.drbandrade.shoppingapp.repositories;

import br.com.drbandrade.shoppingapp.models.Order;
import br.com.drbandrade.shoppingapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
