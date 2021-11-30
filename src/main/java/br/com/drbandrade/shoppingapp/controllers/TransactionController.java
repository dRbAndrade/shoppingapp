package br.com.drbandrade.shoppingapp.controllers;

import br.com.drbandrade.shoppingapp.dtos.TransactionDTO;
import br.com.drbandrade.shoppingapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionDTO>> findPaged(Pageable pageable){

        Page<TransactionDTO> response = transactionService.findPaged(pageable);
        return ResponseEntity.ok(response);

    }
    @PostMapping("/{userId}/{orderId}/pay")
    public ResponseEntity<TransactionDTO> persistNew(@PathVariable long userId,
                                               @PathVariable long orderId,
                                               @RequestParam double amount){

        TransactionDTO response = new TransactionDTO(userId,orderId);
        response = transactionService.persistNew(response,amount);
        return ResponseEntity.ok(response);

    }


}
