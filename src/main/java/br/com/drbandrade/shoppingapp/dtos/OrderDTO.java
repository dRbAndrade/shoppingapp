package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Order;
import br.com.drbandrade.shoppingapp.models.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private long userID;
    private Map<ProductDTO,Integer> products;
    private double amount;
    private CouponDTO coupon;
    private Status status;
    public OrderDTO() {}

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        //Create a new CouponDTO from Coupon entity
        //Since an order may or may not have a coupon
        //this check avoids null pointer exceptions
        this.coupon = entity.getCoupon()==null?null:new CouponDTO(entity.getCoupon());
        this.status = entity.getStatus();
        this.userID = entity.getUser().getId();
        this.amount = entity.getAmount();
    }

}
