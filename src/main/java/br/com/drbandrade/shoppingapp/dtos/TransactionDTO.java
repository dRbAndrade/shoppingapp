package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Status;
import br.com.drbandrade.shoppingapp.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransactionDTO implements Serializable{

    private Long userId;
    private Long orderId;
    private String id;
    private Status status;
    private String description;

    public TransactionDTO() {
    }

    public TransactionDTO(Long userId, Long orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }

    public TransactionDTO(Transaction entity) {
        this.userId = entity.getOrder()==null?null:entity.getOrder().getUser().getId();
        this.orderId = entity.getOrder()==null?null:entity.getOrder().getId();
        this.id = String.format("tran%09d",entity.getId());
        this.status = entity.getStatus();
        this.description = entity.getDescription();
    }

}
