package br.com.drbandrade.shoppingapp.services.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDoesNotBelongToUserException extends TransactionException{

    public OrderDoesNotBelongToUserException(String message) {
        super(message);
    }

}
