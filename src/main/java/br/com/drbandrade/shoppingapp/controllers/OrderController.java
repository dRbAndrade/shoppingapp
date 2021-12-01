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
@RequestMapping("/orders")
public class OrderController {


    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> findPaged(Pageable pageable) {
        Page<OrderDTO> response = orderService.findPaged(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/order")
    public ResponseEntity<OrderDTO> persistNew(@PathVariable
                                                       long userId,
                                               @RequestParam
                                                       int qty,
                                               @RequestParam(required = false)
                                                       String coupon) {
        OrderDTO response = new OrderDTO();
        //The orders can have more than one product. But since we are
        //using only one hypothetical product, I create the product-quantity
        //map and populate it with the only product we have available
        //but this make it able to expand to more products rather easily
        //via @RequestBody, for example
        HashMap<ProductDTO, Integer> productMap = new HashMap<>();
        productMap.put(new ProductDTO(1L), qty);
        CouponDTO couponDTO = coupon == null ? null : new CouponDTO(coupon.replace("\"", ""));
        //This removes the " " from the coupon string param before creating
        //a new DTO with its value. Since coupon is not a required param,
        //it also checks if it's present before trying to access its
        //value to avoid null pointer exceptions
        response.setCoupon(couponDTO);
        response.setUserID(userId);
        response = orderService.persistNew(productMap, response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO dto) {
        OrderDTO response = orderService.update(id, dto);
        return ResponseEntity.ok(response);
    }

}
