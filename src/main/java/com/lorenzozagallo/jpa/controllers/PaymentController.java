package com.lorenzozagallo.jpa.controllers;

import com.lorenzozagallo.jpa.dtos.PaymentRecordDto;
import com.lorenzozagallo.jpa.models.Payment;
import com.lorenzozagallo.jpa.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workshop/payments")
public class PaymentController {

    @Autowired
    public PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        List<Payment> payments = paymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> findById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findById(id);
        return payment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRecordDto paymentRecordDto) {
        Payment payment = paymentService.createPayment(paymentRecordDto);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
