package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Order;
import br.com.drbandrade.shoppingapp.models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDTO {

    private Long id;
    private String name;
    private List<OrderDTO> orders;
    private Set<CouponDTO> usedCoupons;

    public UserDTO() {
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.orders = entity.getOrders().stream().map(OrderDTO::new).collect(Collectors.toList());
        this.usedCoupons = entity.getUsedCoupons().stream().map(CouponDTO::new).collect(Collectors.toSet());
    }

    public UserDTO(Long id) {
        this.id = id;
    }
}
