package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private long userID;
    private Map<ProductDTO,Integer> products;
    private double amount;
    private CouponDTO coupon;
    private OrderStatus status;
    public OrderDTO() {}

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.coupon = entity.getCoupon()==null?null:new CouponDTO(entity.getCoupon());
        this.status = entity.getStatus();
        this.userID = entity.getUser().getId();
        this.amount = entity.getAmount();
    }

}
