package br.com.drbandrade.shoppingapp.models;

import br.com.drbandrade.shoppingapp.dtos.CouponDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "tb_Coupon")
public class Coupon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double value;

    public Coupon() {}

    public Coupon(String name) {
        this.name = name;
    }

    public Coupon(CouponDTO dto) {
        this.name = dto.getName();
        this.value = dto.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Double.compare(coupon.value, value) == 0 && Objects.equals(id, coupon.id) && Objects.equals(name, coupon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }
}
