package br.com.drbandrade.shoppingapp.services;

import br.com.drbandrade.shoppingapp.dtos.*;
import br.com.drbandrade.shoppingapp.models.Order;
import br.com.drbandrade.shoppingapp.models.OrderProduct;
import br.com.drbandrade.shoppingapp.models.Transaction;
import br.com.drbandrade.shoppingapp.models.User;
import br.com.drbandrade.shoppingapp.repositories.OrderRepository;
import br.com.drbandrade.shoppingapp.repositories.TransactionRepository;
import br.com.drbandrade.shoppingapp.repositories.UserRepository;
import br.com.drbandrade.shoppingapp.services.exceptions.OrderNotFoundException;
import br.com.drbandrade.shoppingapp.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public UserService(UserRepository userRepository, OrderRepository orderRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }



    @Transactional
    public UserDTO persistNew(UserDTO dto){
        User entity = userRepository.save(new User(dto));
        return new UserDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findPaged(Pageable pageable){

        Page<User> entityPage = userRepository.findAll(pageable);
        Page<UserDTO> response = entityPage.map(UserDTO::new);
        final int[] userIndex = {0};
        //this is the same conversion that was required
        //at the OrderService. But here we have one extra layer
        //since we are getting the orders from the user now.
        entityPage.forEach(user->{
            final int[] orderIndex = {0};
            user.getOrders().forEach(order->{
                List<OrderProduct> orderProducts = order.getProducts();
                Map<ProductDTO,Integer> products = new HashMap<>();
                orderProducts.forEach(element->products.put(new ProductDTO(element.getProduct()),element.getQuantity()));
                OrderDTO orderDTO = new OrderDTO(order);
                orderDTO.setProducts(products);
                ((UserDTO)response.get().toArray()[userIndex[0]]).getOrders().set(orderIndex[0]++,orderDTO);
            });
            userIndex[0]++;
        });
        return response;
    }

    @Transactional
    public UserDTO update(long id,UserDTO dto){
        User entity = userRepository.getById(id);
        entity.setName(dto.getName());
        entity.setOrders(dto.getOrders().stream().map(Order::new).collect(Collectors.toList()));
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<OrderSimplifiedDTO> findOrdersById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException(String.format("No user with id: %d was found",id))
        );
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(OrderSimplifiedDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderTransactionsDTO findOrderTransactions(Long userId, Long orderId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new UserNotFoundException(String.format("No user with id %d was found",userId))
        );
        Order order;
        if(user.getOrders().contains(new Order(orderId))) order = orderRepository.getById(orderId);
        else throw new OrderNotFoundException(String.format("No order with id %d was found for user with id %d",orderId,userId),orderId);
        List<Transaction> transactions = transactionRepository.findByOrderId(orderId);
        return new OrderTransactionsDTO(order,transactions);
    }
}
