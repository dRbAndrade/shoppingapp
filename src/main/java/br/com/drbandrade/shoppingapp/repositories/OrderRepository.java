package br.com.drbandrade.shoppingapp.repositories;

import br.com.drbandrade.shoppingapp.models.Order;
import br.com.drbandrade.shoppingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);

}
