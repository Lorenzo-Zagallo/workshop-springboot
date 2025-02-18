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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id)
                .or(() -> { throw new ResourceNotFoundException("Pagamento não encontrado para o ID: " + id); });
    }

    @Transactional
    public Payment createPayment(PaymentRecordDto paymentRecordDto) {
        try {
            Order order = orderRepository.findById(paymentRecordDto.orderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // criar o Payment
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
        try {
            Payment entity = paymentRepository.getReferenceById(id);
            Optional.ofNullable(payment.getMoment()).ifPresent(entity::setMoment);
            return paymentRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Pagamento não encontrado para o ID: " + id);
        }
    }
}
