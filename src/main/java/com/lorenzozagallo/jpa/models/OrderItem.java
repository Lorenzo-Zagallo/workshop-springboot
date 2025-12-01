package com.lorenzozagallo.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lorenzozagallo.jpa.models.pk.OrderItemPK;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    // @MapsId("orderId") significa: "Pegue o ID deste objeto Order e coloque dentro
    // de id.orderId"
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonIgnore // Evita o loop infinito no JSON
    private Order order;

    // @MapsId("productId") significa: "Pegue o ID deste objeto Product e coloque
    // dentro de id.productId"
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    // Sem @JsonIgnore aqui, pois queremos ver o produto no detalhe do pedido!
    private Product product;

    private Integer quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, Integer quantity, Double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        // O JPA preenche o ID automaticamente graças ao @MapsId,
        // mas podemos forçar para garantir em testes unitários:
        this.id.setOrderId(order.getId());
        this.id.setProductId(product.getId());
    }

    // Getters e Setters corrigidos para usar os atributos da classe, não do ID

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSubTotal() {
        return price * quantity;
    }

    // Demais Getters e Setters
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
}