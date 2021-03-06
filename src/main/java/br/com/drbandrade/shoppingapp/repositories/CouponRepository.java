package br.com.drbandrade.shoppingapp.repositories;

import br.com.drbandrade.shoppingapp.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    Optional<Coupon> findByName(String name);

}
