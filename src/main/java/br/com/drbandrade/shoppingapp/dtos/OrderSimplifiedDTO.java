package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Order;
import lombok.Getter;
import lombok.Setter;

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
        //Same as the OrderDTO
        this.coupon = entity.getCoupon()==null?null:entity.getCoupon().getName();
        this.amount = entity.getAmount();
        //Since Instant class, which was used to persist the date information,
        //uses UTC time zone, this adapts time to your system's default timezone
        this.orderedDate = ZonedDateTime.ofInstant(entity.getDate(), ZoneId.systemDefault());
    }

}
