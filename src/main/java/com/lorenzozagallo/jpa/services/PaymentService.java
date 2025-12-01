package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.PaymentRecordDto;
import com.lorenzozagallo.jpa.models.Order;
import com.lorenzozagallo.jpa.models.Payment;
import com.lorenzozagallo.jpa.repositories.OrderRepository;
import com.lorenzozagallo.jpa.repositories.PaymentRepository;
import com.lorenzozagallo.jpa.services.exceptions.DatabaseException;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PaymentService {

    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public List<Payment> findAll() {
        LOGGER.info("Buscando todos os pagamentos");
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        LOGGER.info("Buscando pagamento com ID: " + id);
        return paymentRepository.findById(id)
                .orElseThrow(() -> {
                        LOGGER.warning("Pagamento não encontrado para o ID: " + id);
                        throw new ResourceNotFoundException("Pagamento não encontrado para o ID: " + id);
                    });
    }

    @Transactional
    public Payment createPayment(PaymentRecordDto paymentRecordDto) {
        LOGGER.info("Criando pagamento para o pedido ID: " + paymentRecordDto.orderId());
        try {
            Order order = orderRepository.findById(paymentRecordDto.orderId())
                    .orElseThrow(() -> {
                        LOGGER.warning("Pedido não encontrado para o ID: " + paymentRecordDto.orderId());
                        return new RuntimeException("Order not found");
                    });

            // Cria o Payment
            Payment payment = new Payment();
            payment.setMoment(paymentRecordDto.moment());
            payment.setOrder(order); // associar o Order ao Payment

            // salvar o Payment no banco de dados
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar o pagamento: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Excluindo pagamento com ID: " + id);
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pagamento não encontrado para o ID: " + id);
        }
        try {
            paymentRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro de integridade referencial ao excluir o pagamento: " + e.getMessage());
        }
    }

    @Transactional
    public Payment update(Long id, Payment payment) {
        LOGGER.info("Atualizando pagamento com ID: " + id);
        try {
            Payment entity = paymentRepository.getReferenceById(id);
            if (payment.getMoment() != null) {
                entity.setMoment(payment.getMoment());
            }
            return paymentRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Pagamento não encontrado para o ID: " + id);
        }
    }
}
