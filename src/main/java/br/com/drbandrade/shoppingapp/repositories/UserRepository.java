package br.com.drbandrade.shoppingapp.repositories;

import br.com.drbandrade.shoppingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
