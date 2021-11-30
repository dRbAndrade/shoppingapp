package br.com.drbandrade.shoppingapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class OrderProductKey implements Serializable {

    @Column(name = "order_id")
    private long orderid;
    @Column(name = "product_id")
    private long productid;

    public OrderProductKey() {
    }

    public OrderProductKey(Order order, Product product) {
        this.orderid = order.getId();
        this.productid = product.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductKey that = (OrderProductKey) o;
        return orderid == that.orderid && productid == that.productid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderid, productid);
    }
}
