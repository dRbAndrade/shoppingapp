package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
public class OrderSimplifiedDTO {

    private Long id;
    private double amount;
    private ZonedDateTime orderedDate;
    private String coupon;

    public OrderSimplifiedDTO() {}

    public OrderSimplifiedDTO(Order entity) {
        this.id = entity.getId();
        this.coupon = entity.getCoupon()==null?null:entity.getCoupon().getName();
        this.amount = entity.getAmount();
        this.orderedDate = ZonedDateTime.ofInstant(entity.getDate(), ZoneId.systemDefault());
    }

}
