package br.com.drbandrade.shoppingapp.models;

import br.com.drbandrade.shoppingapp.dtos.OrderDTO;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tb_order")
public class Order implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    List<OrderProduct>  products;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    private OrderStatus status;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Instant date;

    public Order() {}

    public Order(OrderDTO dto) {
        this.coupon = dto.getCoupon()==null?null:new Coupon(dto.getCoupon());
        this.status = dto.getCoupon()==null?null:dto.getStatus();
    }

    public Order(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PrePersist
    public void prePersist(){
        date = Instant.now();
    }
}
