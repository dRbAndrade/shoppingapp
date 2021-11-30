package br.com.drbandrade.shoppingapp.models;

import br.com.drbandrade.shoppingapp.dtos.OrderDTO;
import lombok.Getter;
import lombok.Setter;

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
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @Enumerated(EnumType.STRING)
    private Status status;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Instant date;

    public Order() {}

    public Order(OrderDTO dto) {
        this.coupon = dto.getCoupon()==null?null:new Coupon(dto.getCoupon());
        this.status = dto.getStatus();
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", products=" + products +
                ", coupon=" + coupon +
                ", status=" + status +
                ", amount=" + amount +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
