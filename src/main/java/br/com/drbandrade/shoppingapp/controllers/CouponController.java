package br.com.drbandrade.shoppingapp.controllers;

import br.com.drbandrade.shoppingapp.dtos.CouponDTO;
import br.com.drbandrade.shoppingapp.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity<CouponDTO> persistNew(@RequestBody CouponDTO dto){
        CouponDTO response = service.persistNew(dto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<CouponDTO> update(@PathVariable Long id, @RequestBody CouponDTO dto){
        CouponDTO response = service.update(id,dto);
        return ResponseEntity.ok(response);
    }

}
