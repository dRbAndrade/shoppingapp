package br.com.drbandrade.shoppingapp.services.exceptions;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidAmountException extends TransactionException{

    public InvalidAmountException(String message) {
        super(message);
    }
}
