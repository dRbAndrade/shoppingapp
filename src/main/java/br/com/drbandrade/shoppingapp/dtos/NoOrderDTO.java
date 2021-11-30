package br.com.drbandrade.shoppingapp.dtos;

import lombok.Getter;
import lombok.Setter;

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
