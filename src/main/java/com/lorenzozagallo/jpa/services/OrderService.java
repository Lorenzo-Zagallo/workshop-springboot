package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.OrderItemRecordDto;
import com.lorenzozagallo.jpa.dtos.OrderRecordDto;
import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.models.OrderItem;
import com.lorenzozagallo.jpa.models.Product;
import com.lorenzozagallo.jpa.models.User;
import com.lorenzozagallo.jpa.repositories.OrderRepository;
import com.lorenzozagallo.jpa.repositories.UserRepository;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    public List<Order> findAll() {
        LOGGER.info("Buscando todos os pedidos");
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        LOGGER.info("Buscando pedido com ID: " + id);
        return orderRepository.findById(id)
                .orElseThrow(() -> { 
                    LOGGER.warning("Pedido não encontrado para o ID: " + id);
                    return new ResourceNotFoundException("Pedido não encontrado para o ID: " + id);
                });
    }

    @Transactional
    public Order save(OrderRecordDto orderRecordDto) {
        LOGGER.info("Salvando novo pedido para o cliente ID: " + orderRecordDto.clientId());
        try {
            // Busca o usuário (Client)
            User client = userRepository.findById(orderRecordDto.clientId())
                    .orElseThrow(() -> {
                        LOGGER.warning("Usuário não encontrado para o ID: " + orderRecordDto.clientId());
                        return new ResourceNotFoundException("Usuário não encontrado para o ID: " + orderRecordDto.clientId());
                    });

            // Instancia o Pedido
            Order order = new Order();
            order.setMoment(Instant.now());
            order.setOrderStatus(orderRecordDto.orderStatus());
            order.setClient(client);

            // Processa os Itens
            List<OrderItem> orderItems = orderRecordDto.items().stream()
                    .map(dto -> {
                        Product product = productService.findById(dto.productID());
                        return new OrderItem(order, product, dto.quantity(), dto.price());
                    }).toList();

            order.getItems().addAll(orderItems);

            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade ao salvar o pedido.");
        }
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Excluindo pedido com ID: " + id);
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido não encontrado para o ID: " + id);
        }
        try {
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não é possível excluir um pedido que possui itens ou pagamentos associados.");
        }
    }

    @Transactional
    public Order update(Long id, OrderRecordDto dto) {
        LOGGER.info("Atualizando pedido com ID: " + id);
        Order entity = findById(id);
        updateData(entity, dto);
        return orderRepository.save(entity);
    }

    // Método auxiliar para atualizar dados (apenas status por enquanto, já que não
    // se muda itens facilmente em update)
    private void updateData(Order entity, OrderRecordDto dto) {
        if (dto.orderStatus() != null)
            entity.setOrderStatus(dto.orderStatus());
        // Lógica de update de pedido geralmente é restrita
    }

    // Método EXTRA que você queria (Adicionar item em pedido existente)
    @Transactional
    public Order addItemToOrder(Long orderId, OrderItemRecordDto itemDto) {
        LOGGER.info("Adicionando item ao pedido ID: " + orderId);
        Order order = findById(orderId); // Já lança erro se não achar
        Product product = productService.findById(itemDto.productID()); // Já lança erro se não achar

        OrderItem newItem = new OrderItem(order, product, itemDto.quantity(), itemDto.price());
        order.getItems().add(newItem);

        return orderRepository.save(order);
    }
}