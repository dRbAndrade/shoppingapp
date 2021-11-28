package br.com.drbandrade.shoppingapp.services.exceptions;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAlreadyPaidException extends TransactionException{


    public OrderAlreadyPaidException(String message, FailedTransactionDTO dto) {
        super(message, dto);
    }
}
