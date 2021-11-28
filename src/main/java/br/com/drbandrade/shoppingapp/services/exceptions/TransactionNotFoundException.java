package br.com.drbandrade.shoppingapp.services.exceptions;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
