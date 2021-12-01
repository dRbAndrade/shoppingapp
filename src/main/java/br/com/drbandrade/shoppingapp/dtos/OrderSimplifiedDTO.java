package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class OrderSimplifiedDTO {

    private Long id;
    private double amount;
    private String orderedDate;
    private String coupon;

    public OrderSimplifiedDTO() {}

    public OrderSimplifiedDTO(Order entity) {
        this.id = entity.getId();
        //Same as the OrderDTO
        this.coupon = entity.getCoupon()==null?null:entity.getCoupon().getName();
        this.amount = entity.getAmount();
        //Since Instant class, which was used to persist the date information,
        //uses UTC time zone, this adapts time to your system's default timezone
        //and formats it to not show the time
        this.orderedDate = ZonedDateTime
                .ofInstant(entity.getDate(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public OrderSimplifiedDTO(Long id, double amount, String orderedDate, String coupon) {
        this.id = id;
        this.amount = amount;
        this.orderedDate = orderedDate;
        this.coupon = coupon;
    }

}
