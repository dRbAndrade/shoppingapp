package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.OrderStatus;
import br.com.drbandrade.shoppingapp.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FailedTransactionDTO implements Serializable{

    private Long userId;
    private Long orderId;
    private String id;
    private OrderStatus status;
    private String description;

    public FailedTransactionDTO() {
    }

    public FailedTransactionDTO(Transaction entity) {
        this.userId = entity.getUser().getId();
        this.orderId = entity.getOrder().getId();
        this.id = String.format("tran%09d",entity.getId());
        this.status = entity.getStatus();
        this.description = entity.getDescription();
    }

    public FailedTransactionDTO(TransactionDTO dto) {
        this.userId = dto.getUserId();
        this.orderId = dto.getOrderId();
        this.id = dto.getId();
        this.status = dto.getStatus();
    }

}
