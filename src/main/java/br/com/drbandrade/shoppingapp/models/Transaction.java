package br.com.drbandrade.shoppingapp.models;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import br.com.drbandrade.shoppingapp.dtos.TransactionDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tb_transaction")
public class Transaction implements Serializable{

    @ManyToOne
    private Order order;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;

    public Transaction() {
    }

    public Transaction(TransactionDTO dto) {
        this.order = new Order(dto.getOrderId());
        //As the DTO's id is prepended with "tran" and padded
        //with 0s to the left, this removes the letters and
        //casts the string to a numeric value and also
        //avoids null pointer exceptions as DTOs might not
        //have an ID initiated
        this.id = dto.getId()==null?null:Long.valueOf(dto.getId().replaceAll("[^0-9]",""));
        this.status = dto.getStatus();
        this.description = null;
    }
    public Transaction(FailedTransactionDTO dto) {
        this.order = dto.getOrderId()==null?null:new Order(dto.getOrderId());
        //Same as above
        this.id = dto.getId()==null?null:Long.valueOf(dto.getId().replaceAll("[^0-9]",""));
        this.status = dto.getStatus();
        this.description = dto.getDescription();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "order=" + order +
                ", id=" + id +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
