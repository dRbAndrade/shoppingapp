package br.com.drbandrade.shoppingapp.repositories;

import br.com.drbandrade.shoppingapp.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT t FROM Transaction t WHERE t.order.id = :orderId")
    List<Transaction> findByOrderId(@Param("orderId") Long orderId);

}
