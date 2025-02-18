package com.lorenzozagallo.jpa.models;

import com.lorenzozagallo.jpa.models.pk.OrderItemPK;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private Integer quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Optional<Order> order, Optional<Product> product, Integer quantity, Double price) {
        this.order = order.orElse(null);
        this.product = product.orElse(null);
        this.quantity = quantity;
        this.price = price;
    }

    @ManyToOne
    @MapsId("orderId") // mapeia a chave composta 'orderId' da classe OrderItemPK
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @MapsId("productId") // mapeia a chave composta 'productId' da classe OrderItemPK
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    public double getSubTotal() {
        return price * quantity;
    }


    public OrderItemPK getId() {
        return id;
    }

    public void setId(OrderItemPK id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Optional<Order> getOrder() {
        return Optional.ofNullable(order);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Optional<Product> getProduct() {
        return Optional.ofNullable(product);
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
