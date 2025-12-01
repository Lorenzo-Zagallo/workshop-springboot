package com.lorenzozagallo.jpa.services;

import com.lorenzozagallo.jpa.dtos.UserRecordDto;
import com.lorenzozagallo.jpa.models.User;
import com.lorenzozagallo.jpa.repositories.UserRepository;
import com.lorenzozagallo.jpa.services.exceptions.DatabaseException;
import com.lorenzozagallo.jpa.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        LOGGER.info("Buscando todos os usuários");
        return userRepository.findAll();
    }

    public User findById(Long id) {
        LOGGER.info("Buscando usuário com ID: " + id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warning("Usuário não encontrado para o ID: " + id);
                    return new ResourceNotFoundException("Usuário não encontrado para o ID: " + id);
                });
    }

    @Transactional
    public User save(UserRecordDto userRecordDto) {
        LOGGER.info("Salvando novo usuário: " + userRecordDto.name());
        User user = new User();
        user.setName(userRecordDto.name());
        user.setEmail(userRecordDto.email());
        user.setPhone(userRecordDto.phone());
        user.setPassword(userRecordDto.password());
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Excluindo usuário com ID: " + id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado para o ID: " + id);
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir usuário que possui pedidos relacionados.");
        }
    }

    @Transactional
    public User update(Long id, UserRecordDto userRecordDto) {
        LOGGER.info("Atualizando usuário com ID: " + id);
        User entity = findById(id);
        updateData(entity, userRecordDto);
        return userRepository.save(entity);
    }

    private void updateData(User entity, UserRecordDto userRecordDto) {
        if (userRecordDto.name() != null)
            entity.setName(userRecordDto.name());
        if (userRecordDto.email() != null)
            entity.setEmail(userRecordDto.email());
        if (userRecordDto.phone() != null)
            entity.setPhone(userRecordDto.phone());
        // não se atualiza senha aqui, mas em endpoint específico
    }
}