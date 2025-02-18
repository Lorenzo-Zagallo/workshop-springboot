/* REQUISIÇÕES NO POSTMAN


`POST /users`
{
  "name": "João Silva",
  "email": "joao.silva@example.com",
  "phone": "11999999999",
  "password": "senha123"
}


`POST /categories`
{
  "name": "Eletrônicos"
}



`POST /products`
{
  "name": "Smartphone XYZ",
  "description": "Um smartphone com ótimas funcionalidades.",
  "price": 2500.00,
  "imgUrl": "https://example.com/smartphone.jpg"
}


`POST /orders`
{
  "moment": "2024-02-18T10:30:00Z",
  "orderStatus": "ORDER_STATUS",
  "clientId": "UUID_DO_USUÁRIO",
  "items": [
    {
      "productID": "UUID_DO_PRODUTO",
      "quantity": 2,
      "price": 2500.00
    }
  ]
}


`POST /payments`
{
  "moment": "2024-02-18T11:00:00Z",
  "orderId": "ID_DO_PEDIDO"
}

`PUT /orders/{orderId}/items`
{
  "productID": 123,
  "quantity": 2,
  "price": 19.99
}




**ordem correta para inserção**
1. **User** → 2. **Category** → 3. **Product**
→ 4. **Order** (com itens) → 5. **Payment** → 6. **OrdemItem**



*/

/* TEST CONFIG COM H2 (dá pra usar como consulta para os dados na requisição)


package com.lorenzozagallo.jpa.config;

import com.lorenzozagallo.jpa.models.*;
import com.lorenzozagallo.jpa.models.enums.OrderStatus;
import com.lorenzozagallo.jpa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {

        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Computers");

        Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
        Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
        Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
        Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
        Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        p1.getCategories().add(cat2);
        p2.getCategories().add(cat1);
        p2.getCategories().add(cat3);
        p3.getCategories().add(cat3);
        p4.getCategories().add(cat3);
        p5.getCategories().add(cat2);

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
        User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456");

        Order o1 = new Order(null, Instant.parse("2024-11-20T19:53:07.000-03:00"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2024-12-21T03:42:10.000-03:00"), OrderStatus.WAITING_PAYMENT,u2);
        Order o3 = new Order(null, Instant.parse("2024-12-22T15:21:22.000-03:00"), OrderStatus.CANCELED,u1);

        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));

        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

        Payment pay1 = new Payment(null, Instant.parse("2024-11-20T21:53:07.000-03:00"), o1);
        o1.setPayment(pay1);

        orderRepository.save(o1);
    }}
*/




