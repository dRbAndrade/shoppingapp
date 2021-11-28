package br.com.drbandrade.shoppingapp.dtos;

import br.com.drbandrade.shoppingapp.models.Coupon;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class CouponDTO {

    private Long id;
    private String name;
    private double value;

    public CouponDTO() {}

    public CouponDTO(Coupon entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.value = entity.getValue();
    }

    public CouponDTO(String name) {
        this.name = name;
        this.value = Double.parseDouble(name.replaceAll("[^0-9]",""))/100;
    }

    @Override
    public String toString() {
        return "Coupon : " + name;
    }
}
