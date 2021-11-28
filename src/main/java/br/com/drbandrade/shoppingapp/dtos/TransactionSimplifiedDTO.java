package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.OrderStatus;
import br.com.drbandrade.shoppingapp.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransactionSimplifiedDTO implements Serializable{

    private String id;
    private OrderStatus status;

    public TransactionSimplifiedDTO() {
    }

    public TransactionSimplifiedDTO(TransactionDTO dto){
        this.id = dto.getId();
        this.status = dto.getStatus();
    }

    public TransactionSimplifiedDTO(Transaction entity) {
        this.id = String.format("tran%09d",entity.getId());
        this.status = entity.getStatus();
    }

}
