package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Order;
import br.com.drbandrade.shoppingapp.models.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderTransactionsDTO {

    private Long id;
    private double amount;
    private String orderedDate;
    private String coupon;
    private List<TransactionSimplifiedDTO> transactions;

    public OrderTransactionsDTO() {}

    public OrderTransactionsDTO(Order entity, List<Transaction> transactions) {
        this.id = entity.getId();
        //Same as OrderDTO
        this.coupon = entity.getCoupon()==null?null:entity.getCoupon().getName();
        this.amount = entity.getAmount();
        //Same as OrderSimplifiedDTO
        this.orderedDate = ZonedDateTime
                .ofInstant(entity.getDate(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.transactions = transactions.stream().map(TransactionSimplifiedDTO::new).collect(Collectors.toList());
    }

}
