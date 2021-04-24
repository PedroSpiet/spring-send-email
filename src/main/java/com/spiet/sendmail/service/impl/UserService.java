package com.spiet.sendmail.service.impl;

import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;
import com.spiet.sendmail.exceptions.BusinessException;
import com.spiet.sendmail.repositories.UserRepository;
import com.spiet.sendmail.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository repository;
    private ModelMapper mapper;

    public UserService(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User createUser(UserDTO user) {
        return repository.save(mapper.map(user, User.class));
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<User> find(UserDTO userDTO, Pageable pageable) {
        User user = mapper.map(userDTO, User.class);

        Example<User> example = Example.of(user, ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageable);
    }
}
