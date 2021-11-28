package br.com.drbandrade.shoppingapp.services.exceptions;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionException extends RuntimeException{

    private FailedTransactionDTO dto;

    public TransactionException(String message, FailedTransactionDTO dto) {
        super(message);
        this.dto = dto;
    }

}
