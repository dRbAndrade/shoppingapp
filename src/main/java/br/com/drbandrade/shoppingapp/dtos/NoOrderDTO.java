package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NoOrderDTO {

    private Long orderId;
    private String description;

    public NoOrderDTO() {}

    public NoOrderDTO(Long id) {
        this.orderId = id;
        description = "Order not found";

    }

}
