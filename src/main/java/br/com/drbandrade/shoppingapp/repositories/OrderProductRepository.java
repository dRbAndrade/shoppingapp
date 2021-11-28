package br.com.drbandrade.shoppingapp.repositories;

import br.com.drbandrade.shoppingapp.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
