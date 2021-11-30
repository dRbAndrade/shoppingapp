package br.com.drbandrade.shoppingapp.controllers.exception;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import br.com.drbandrade.shoppingapp.dtos.NoOrderDTO;
import br.com.drbandrade.shoppingapp.services.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<String> handleInvalidQuantity(InvalidArgumentException ex, WebRequest request){
        return ResponseEntity.status(404).body("Invalid quantity.");
    }

    @ExceptionHandler(CouponException.class)
    public ResponseEntity<String> handleInvalidCoupon(CouponException ex, WebRequest request){
        return ResponseEntity.status(404).body("Invalid coupon");
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<FailedTransactionDTO> handleInvalidAmount(InvalidAmountException ex, WebRequest request){
        return ResponseEntity.status(400).body(ex.getDto());
    }

    @ExceptionHandler(BankRejectionException.class)
    public ResponseEntity<FailedTransactionDTO> handleBankRejection(BankRejectionException ex, WebRequest request){
        return ResponseEntity.status(400).body(ex.getDto());
    }

    @ExceptionHandler(TransactionOrderNotFoundException.class)
    public ResponseEntity<FailedTransactionDTO> handleTransactionOrderNotFound(TransactionOrderNotFoundException ex, WebRequest request){
        return ResponseEntity.status(400).body(ex.getDto());
    }

    @ExceptionHandler(ServerResponseException.class)
    public ResponseEntity<FailedTransactionDTO> handleServerFail(ServerResponseException ex, WebRequest request){
        return ResponseEntity.status(400).body(ex.getDto());
    }

    @ExceptionHandler(OrderAlreadyPaidException.class)
    public ResponseEntity<FailedTransactionDTO> handleAlreadyPaid(OrderAlreadyPaidException ex, WebRequest request){
        return ResponseEntity.status(400).body(ex.getDto());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<NoOrderDTO> handleOrderNotFound(OrderNotFoundException ex, WebRequest request){
        NoOrderDTO response = new NoOrderDTO(ex.getOrderId());
        return ResponseEntity.status(404).body(response);
    }

}
