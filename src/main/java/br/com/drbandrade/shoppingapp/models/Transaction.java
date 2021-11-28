package br.com.drbandrade.shoppingapp.models;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import br.com.drbandrade.shoppingapp.dtos.TransactionDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_transaction")
public class Transaction implements Serializable{

    @ManyToOne
    private User user;
    @ManyToOne
    private Order order;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrderStatus status;
    private String description;

    public Transaction() {
    }

    public Transaction(TransactionDTO dto) {
        this.user = new User(dto.getUserId());
        this.order = new Order(dto.getOrderId());
        this.id = dto.getId()==null?null:Long.valueOf(dto.getId().replaceAll("[^0-9]",""));
            this.status = dto.getStatus();
        this.description = null;
    }
    public Transaction(FailedTransactionDTO dto) {
        this.user = new User(dto.getUserId());
        this.order = new Order(dto.getOrderId());
        this.id = dto.getId()==null?null:Long.valueOf(dto.getId().replaceAll("[^0-9]",""));
        this.status = dto.getStatus();
        this.description = dto.getDescription();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "user=" + user +
                ", order=" + order +
                ", id=" + id +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
