package br.com.drbandrade.shoppingapp.services;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import br.com.drbandrade.shoppingapp.dtos.TransactionDTO;
import br.com.drbandrade.shoppingapp.models.*;
import br.com.drbandrade.shoppingapp.repositories.*;
import br.com.drbandrade.shoppingapp.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionDTO persistNew(TransactionDTO dto,double amount) {

        Random random = new Random();
        Transaction entity = null;
        //this mocks the transaction payment to a random of 6 cases
        switch (random.nextInt(6)){
            //this method avoids a lot of boilerplate
            case 1: failTransaction(dto,new InvalidAmountException("Payment Failed as amount is invalid"));break;
            case 2: failTransaction(dto,new BankRejectionException("Payment Failed from bank"));break;
            case 3: failTransaction(dto,new TransactionOrderNotFoundException("Payment Failed due to invalid order id"));break;
            case 4: failTransaction(dto,new ServerResponseException("No response from payment server"));break;
            case 5: failTransaction(dto,new OrderAlreadyPaidException("Order is already paid for"));break;
            default: {
                dto.setStatus(Status.SUCCESSFUL);
                entity = transactionRepository.save(new Transaction(dto));
            }
        }
        return new TransactionDTO(entity);
    }

    private void failTransaction(TransactionDTO dto,TransactionException ex) {
        //This creates a DTO specifically to build the failed
        //message requested on the assignment requirements and
        //throws the personalized exceptions, so it can be treated
        //on the controller advisor. Setting the DTO as a field in the exception
        // makes it very easy to transmit its values to the advisor
        FailedTransactionDTO failedTransaction = new FailedTransactionDTO(dto);
        failedTransaction.setStatus(Status.FAILED);
        failedTransaction.setDescription(ex.getMessage());
        Long id = transactionRepository.save(new Transaction(failedTransaction)).getId();
        failedTransaction.setId(String.format("tran%09d",id));
        ex.setDto(failedTransaction);
        throw ex;
    }

    @Transactional(readOnly = true)
    public Page<TransactionDTO> findPaged(Pageable pageable){
        return transactionRepository.findAll(pageable).map(TransactionDTO::new);
    }

}
