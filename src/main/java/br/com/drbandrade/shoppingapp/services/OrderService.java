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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CouponRepository couponRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderProductRepository orderProductRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository,
                        CouponRepository couponRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    @Transactional
    public OrderDTO persistNew(Map<ProductDTO,Integer> productMap, OrderDTO orderDTO) {

        Order order = new Order(orderDTO);
        String couponName = order.getCoupon()==null?null:order.getCoupon().getName();
        //This asserts the coupon provided by the controller exists otherwise
        //throw a personalized exception to be treated by the advisor to show
        //a personalized response as required by the assignment
        Coupon coupon = couponName==null ? null: couponRepository.findByName(couponName).orElseThrow(
                ()-> new CouponException(String.format("No Coupon with name: %s was found",couponName))
        );
        User user = userRepository.getById(orderDTO.getUserID());
        //this asserts that the coupon was not used by this user before
        //and throws an exception just like above
        if(user.getUsedCoupons().contains(coupon)){
            throw new CouponException("This coupon has already been used by this user");
        }
        Map<Product,Integer> products = new HashMap<>();
        productMap.forEach((product,quantity)->{
            Product p = productRepository.getById(product.getId());
            if(p.getAvailable()<quantity||quantity<0){
                //this asserts that the quantity provided by the controller
                //and the loop makes it easy to expand to multiple products.
                //Exception same as above
                throw new InvalidArgumentException("Invalid quantity");
            }
            //this updates the product ordered and available fields
            //on the database and adds it to the product list of the order
            p.setOrdered(p.getOrdered()+quantity);
            p.setAvailable(p.getAvailable()-quantity);
            p = productRepository.save(p);
            products.put(p,quantity);
        });
        order.setCoupon(coupon);
        order.setUser(user);
        //An initial status for the order so it doesn't start
        //with null or successfull
        order.setStatus(Status.PENDING);
        final double[] productTotal = {0};
        //this calculates the total of all products on the order list.
        //although there's always only one in our case, this makes easy
        //to expand to more products
        products.forEach((product,quantity)-> productTotal[0]+=product.getPrice()*quantity);
        //the value to be discounted from the total if there is a valid coupon
        //if there is no (valid) coupon, standard value of 0 is applied
        double couponValue = order.getCoupon()==null?0.0:order.getCoupon().getValue();
        order.setAmount(productTotal[0]*((1-couponValue)));
        order = orderRepository.save(order);
        //if a coupon was used in this order,
        //we update the user used coupons
        if(coupon!=null)user.getUsedCoupons().add(coupon);
        userRepository.save(user);
        //this updates the intermediate order_product table
        persistOrderProducts(order,products);
        //here we start building our response
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
        //Our entity has a OrderProduct class list to represent
        //the relationship on the database between it and the intermediate
        //table. But our DTO has a much simpler Map class that has just
        //the productDTOs and their quantities.
        //This makes the casting between these two.
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
