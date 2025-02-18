package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.models.Payment;
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

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id)
                .or(() -> { throw new ResourceNotFoundException("Pagamento não encontrado para o ID: " + id); });
    }

    @Transactional
    public Payment save(Payment payment) {
        try {
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
