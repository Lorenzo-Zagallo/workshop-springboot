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

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(UUID id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.get();
    }

    /*public User insertUser(User obj) {
        return userRepository.save(obj);
    }*/
    public User insertUser(UserRecordDto userRecordDto) {
        User user = new User();
        user.setName(userRecordDto.name());
        user.setEmail(userRecordDto.email());
        user.setPhone(userRecordDto.phone());
        user.setPassword(userRecordDto.password());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
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
    public User updateUser(UUID id, User obj) {
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
    /*
    Optional.ofNullable: Verifica se o valor de um campo é null.
    Se não for null, o método ifPresent é chamado, e o valor é
    atribuído ao campo correspondente no objeto entity.
    */
}
