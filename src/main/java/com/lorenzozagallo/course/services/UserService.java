package com.lorenzozagallo.course.services;

import com.lorenzozagallo.course.entities.User;
import com.lorenzozagallo.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // método para retornar todos os usuários do banco de dados
    public List<User> findAll() {
        return repository.findAll();
    }

    // achar um usuário por ID
    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.get();
    }
}
