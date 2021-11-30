package br.com.drbandrade.shoppingapp.controllers;

import br.com.drbandrade.shoppingapp.dtos.CouponDTO;
import br.com.drbandrade.shoppingapp.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService service;

    @GetMapping("/fetchCoupons")
    public ResponseEntity<Page<CouponDTO>> findPaged(Pageable pageable){
        Page<CouponDTO> response = service.findPaged(pageable);
        return ResponseEntity.ok(response);
    }


}
