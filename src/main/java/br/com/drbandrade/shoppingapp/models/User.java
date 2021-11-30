package br.com.drbandrade.shoppingapp.models;

import br.com.drbandrade.shoppingapp.dtos.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orders;
    @ManyToMany
    private Set<Coupon> usedCoupons;

    public User() {
    }

    public User(UserDTO dto) {
        this.name = dto.getName();
        this.orders = getOrders();
    }

    public User(Long id) {
        this.id = id;
    }
}
