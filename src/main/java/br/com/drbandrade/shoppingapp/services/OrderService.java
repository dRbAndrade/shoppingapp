package br.com.drbandrade.shoppingapp.services;

import br.com.drbandrade.shoppingapp.dtos.OrderDTO;
import br.com.drbandrade.shoppingapp.dtos.ProductDTO;
import br.com.drbandrade.shoppingapp.models.*;
import br.com.drbandrade.shoppingapp.repositories.*;
import br.com.drbandrade.shoppingapp.services.exceptions.CouponException;
import br.com.drbandrade.shoppingapp.services.exceptions.InvalidArgumentException;
import br.com.drbandrade.shoppingapp.services.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Transactional
    public OrderDTO persistNew(Map<ProductDTO,Integer> productMap, OrderDTO orderDTO) {

        Order order = new Order(orderDTO);
        String couponName = order.getCoupon()==null?null:order.getCoupon().getName();
        Coupon coupon = couponName==null ? null: couponRepository.findByName(couponName).orElseThrow(
                ()-> new CouponException(String.format("No Coupon with name: %s was found",couponName))
        );
        User user = userRepository.getById(orderDTO.getUserID());
        if(user.getUsedCoupons().contains(coupon)){
            throw new CouponException("This coupon has already been used by this user");
        }
        if(coupon!=null)user.getUsedCoupons().add(coupon);
        user = userRepository.save(user);
        Map<Product,Integer> products = new HashMap<>();
        productMap.forEach((product,quantity)->{
            Product p =  productRepository.getById(product.getId());
            if(p.getAvailable()<quantity||quantity<0){
                throw new InvalidArgumentException("Invalid quantity");
            }
            p.setOrdered(p.getOrdered()+quantity);
            p.setAvailable(p.getAvailable()-quantity);
            p = productRepository.save(p);
            products.put(p,quantity);

        });
        order.setCoupon(coupon);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        final double[] productTotal = {0};
        products.forEach((product,quantity)-> productTotal[0] +=product.getPrice()*quantity);
        double couponValue = order.getCoupon()==null?0.0:order.getCoupon().getValue();
        order.setAmount(productTotal[0]*((1-couponValue)));
        order = orderRepository.save(order);
        persistOrderProducts(order,products);
        OrderDTO response = new OrderDTO(order);
        Map<ProductDTO,Integer> productDTOMap = new HashMap<>();
        products.forEach((product,quantity)->productDTOMap.put(new ProductDTO(product),quantity));
        response.setProducts(productDTOMap);

        return response;

    }

    @Transactional
    private List<OrderProduct> persistOrderProducts(Order order, Map<Product, Integer> products) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        products.forEach((product,quantity) -> {
            orderProducts.add(orderProductRepository.save(new OrderProduct(order,product,quantity)));
        });
        return orderProducts;
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> findPaged(Pageable pageable){
        Page<Order> entityPage =orderRepository.findAll(pageable);
        Page<OrderDTO> response = entityPage.map(OrderDTO::new);
        final int[] index = {0};
        entityPage.forEach(e->{
            List<OrderProduct> orderProducts = e.getProducts();
            Map<ProductDTO,Integer> products = new HashMap<>();
            orderProducts.forEach(element->products.put(new ProductDTO(element.getProduct()),element.getQuantity()));
            ((OrderDTO)response.get().toArray()[index[0]++]).setProducts(products);
        });
        return response;
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(long id){
        Order entity = orderRepository.findById(id).orElseThrow(
                ()->new OrderNotFoundException(String.format("No order with id: %d was found",id),id)
        );
        return new OrderDTO(entity);
    }

    @Transactional
    public OrderDTO update(long id,OrderDTO dto){
        Order entity = orderRepository.getById(id);
        entity.setAmount(dto.getAmount());
        entity.setCoupon(new Coupon(dto.getCoupon()));
        entity.setStatus(dto.getStatus());
        entity = orderRepository.save(entity);
        return new OrderDTO(entity);

    }
    @Transactional
    public void delete(long id){
        orderRepository.deleteById(id);
    }

}
