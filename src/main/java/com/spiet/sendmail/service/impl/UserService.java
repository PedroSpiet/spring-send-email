package com.spiet.sendmail.service.impl;

import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;
import com.spiet.sendmail.exceptions.BusinessException;
import com.spiet.sendmail.repositories.UserRepository;
import com.spiet.sendmail.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
