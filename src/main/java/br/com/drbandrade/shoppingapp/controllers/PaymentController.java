package br.com.drbandrade.shoppingapp.controllers;

import br.com.drbandrade.shoppingapp.dtos.CouponDTO;
import br.com.drbandrade.shoppingapp.dtos.OrderDTO;
import br.com.drbandrade.shoppingapp.dtos.ProductDTO;
import br.com.drbandrade.shoppingapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public ResponseEntity<Page<OrderDTO>> findPaged(Pageable pageable){

        Page<OrderDTO> response = orderService.findPaged(pageable);
        return ResponseEntity.ok(response);

    }
    @PostMapping("/{userId}/order")
    public ResponseEntity<OrderDTO> persistNew(@PathVariable long userId,
                                               @RequestParam int qty,
                                               @RequestParam String coupon){

        OrderDTO response = new OrderDTO();
        HashMap<ProductDTO,Integer> productMap = new HashMap<>();
        productMap.put(new ProductDTO(1L),qty);
        response.setCoupon(new CouponDTO(coupon.replace("\"","")));
        response.setUserID(userId);
        response = orderService.persistNew(productMap, response);

        return ResponseEntity.ok(response);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO dto){
        OrderDTO response = orderService.update(id,dto);
        return ResponseEntity.ok(response);
    }

}
