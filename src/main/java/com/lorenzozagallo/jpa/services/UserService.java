package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.UserRecordDto;
import com.lorenzozagallo.jpa.models.User;
import com.lorenzozagallo.jpa.repositories.UserRepository;
import com.lorenzozagallo.jpa.services.exceptions.DatabaseException;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        logger.info("Buscando todos os usuários");
        return userRepository.findAll();
    }

    public User findById(Long id) {
        logger.info("Buscando usuário com ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para o ID: " + id));
    }

    public User save(UserRecordDto userRecordDto) {
        logger.info("Salvando novo usuário: {}", userRecordDto.name());
        User user = new User();
        user.setName(userRecordDto.name());
        user.setEmail(userRecordDto.email());
        user.setPhone(userRecordDto.phone());
        user.setPassword(userRecordDto.password());
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Excluindo usuário com ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado para o ID: " + id);
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro de integridade referencial ao excluir o usuário. MESSAGE:  " + e.getMessage());
        }
    }

    @Transactional
    public User update(Long id, User obj) {
        logger.info("Atualizando usuário com ID: {}", id);
        try {
            User entity = userRepository.getReferenceById(id);
            updateData(entity, obj);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário não encontrado para o ID: " + id);
        }
    }

    private void updateData(User entity, User user) {
        Optional.ofNullable(user.getName()).ifPresent(entity::setName);
        Optional.ofNullable(user.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(user.getPhone()).ifPresent(entity::setPhone);
    }
}
