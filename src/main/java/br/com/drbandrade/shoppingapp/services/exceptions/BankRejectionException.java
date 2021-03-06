package br.com.drbandrade.shoppingapp.services.exceptions;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankRejectionException extends TransactionException{

    public BankRejectionException(String message) {
        super(message);
    }
}
