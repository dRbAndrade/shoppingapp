package br.com.drbandrade.shoppingapp.controllers;

import br.com.drbandrade.shoppingapp.dtos.OrderSimplifiedDTO;
import br.com.drbandrade.shoppingapp.dtos.OrderTransactionsDTO;
import br.com.drbandrade.shoppingapp.dtos.UserDTO;
import br.com.drbandrade.shoppingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findPaged(Pageable pageable){
        Page<UserDTO> response = service.findPaged(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderSimplifiedDTO>> findSimplifiedOrders(@PathVariable Long id){
        List<OrderSimplifiedDTO> response = service.findOrdersById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<OrderTransactionsDTO> findOrderTransactions(@PathVariable Long userId,
                                                                      @PathVariable Long orderId) {
        OrderTransactionsDTO response = service.findOrderTransactions(userId,orderId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserDTO> persistNew(@RequestBody UserDTO dto){
        UserDTO response = service.persistNew(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto){
        UserDTO response = service.update(id,dto);
        return ResponseEntity.ok(response);
    }

}
