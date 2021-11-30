package br.com.drbandrade.shoppingapp.services.exceptions;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponseException extends TransactionException{


    public ServerResponseException(String message) {
        super(message);
    }
}
