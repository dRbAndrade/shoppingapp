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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    public TransactionDTO persistNew(TransactionDTO dto,double amount) {

        Random random = new Random();
        Transaction entity = null;
        switch (random.nextInt(6)){
            case 1: failTransaction(dto,new InvalidAmountException("Payment Failed as amount is invalid",null));break;
            case 2: failTransaction(dto,new BankRejectionException("Payment Failed from bank",null));break;
            case 3: failTransaction(dto,new TransactionOrderNotFoundException("Payment Failed due to invalid order id",null));break;
            case 4: failTransaction(dto,new ServerResponseException("No response from payment server",null));break;
            case 5: failTransaction(dto,new OrderAlreadyPaidException("Order is already paid for",null));break;
            default: {
                dto.setStatus(OrderStatus.SUCCESSFUL);
                entity = transactionRepository.save(new Transaction(dto));
            }
        }
        return new TransactionDTO(entity);
    }

    private void failTransaction(TransactionDTO dto,TransactionException ex) {
        FailedTransactionDTO failedTransaction = new FailedTransactionDTO(dto);
        failedTransaction.setStatus(OrderStatus.FAILED);
        failedTransaction.setDescription(ex.getMessage());
        Transaction entity = transactionRepository.save(new Transaction(failedTransaction));
        ex.setDto(new FailedTransactionDTO(entity));
        throw ex;
    }

    @Transactional(readOnly = true)
    public Page<TransactionDTO> findPaged(Pageable pageable){
        return transactionRepository.findAll(pageable).map(TransactionDTO::new);
    }

}
