package br.com.drbandrade.shoppingapp.services;

import br.com.drbandrade.shoppingapp.dtos.FailedTransactionDTO;
import br.com.drbandrade.shoppingapp.dtos.TransactionDTO;
import br.com.drbandrade.shoppingapp.models.Order;
import br.com.drbandrade.shoppingapp.models.Status;
import br.com.drbandrade.shoppingapp.models.Transaction;
import br.com.drbandrade.shoppingapp.models.User;
import br.com.drbandrade.shoppingapp.repositories.OrderRepository;
import br.com.drbandrade.shoppingapp.repositories.TransactionRepository;
import br.com.drbandrade.shoppingapp.repositories.UserRepository;
import br.com.drbandrade.shoppingapp.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              OrderRepository orderRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public TransactionDTO persistNew(TransactionDTO dto, double amount) {

        //This check if the user id passed is valid
        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                ()-> new UserNotFoundException(String.format("No user with id: %d was found",dto.getUserId()))
        );
        Optional<Order> orderOptional = orderRepository.findById(dto.getOrderId());
        Order order = new Order();
        //This checks if the order provided exists at all
        if(orderOptional.isEmpty()){
            failTransaction(dto, new TransactionOrderNotFoundException("Payment Failed due to invalid order id"),true);
        }else order = orderOptional.get();
        //If the order exists, this checks if it
        //belongs to the user, avoiding weird behavior
        if(!user.getOrders().contains(order)){
            failTransaction(dto, new OrderDoesNotBelongToUserException(
                String.format("Order with id: %d does not belong to informed user",order.getId())),false);
        }
        //this checks if the order has been already paid before
        if(order.getStatus().equals(Status.SUCCESSFUL)){
            failTransaction(dto,new OrderAlreadyPaidException("Order is already paid for"),false);
        }
        if(order.getAmount()>amount){
            failTransaction(dto,new InvalidAmountException("Payment Failed as amount is invalid"),false);
        }
        Random random = new Random();
        Transaction entity = new Transaction();
        //This mocks the transaction payment to a random of 3 cases
        switch (random.nextInt(3)){
            case 1: failTransaction(dto,new BankRejectionException("Payment Failed from bank"),false);break;
            case 2: failTransaction(dto,new ServerResponseException("No response from payment server"),false);break;
            default: {
                dto.setStatus(Status.SUCCESSFUL);
                order.setStatus(Status.SUCCESSFUL);
                order = orderRepository.save(order);
                entity =  new Transaction(dto);
                entity.setOrder(order);
                entity = transactionRepository.save(entity);
            }
        }
        return new TransactionDTO(entity);
    }

    //this method was created to avoid boilerplate on "persistNew" method
    private void failTransaction(TransactionDTO dto,TransactionException ex,boolean nullOrder) {
        //This method creates a DTO specifically to build the failed
        //message requested on the assignment requirements and
        //throws the personalized exceptions, so it can be treated
        //on the controller advisor. Setting the DTO as a field in the exception
        //makes it very easy to transmit its values to the advisor

        //The nullOrder param dictates if we should persist or not
        //the order id onto the database. When an order fails because of
        //an invalid ID, persisting it onto the database causes integrity
        //violation, since that order can't be related to the transaction
        //since it doesn't currently exist. Even if we persist only the id number,
        //without relating the tables, it could still generate confusion later
        //since a non-existing ID, let's say 86, doesn't exist now it could
        //exist later on, and it would be related to this transaction via its id
        FailedTransactionDTO failedTransaction = new FailedTransactionDTO(dto);
        failedTransaction.setStatus(Status.FAILED);
        failedTransaction.setDescription(ex.getMessage());
        if(nullOrder)failedTransaction.setOrderId(null);
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
