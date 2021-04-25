package com.spiet.sendmail.resources;

import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;
import com.spiet.sendmail.service.IUserService;
import com.spiet.sendmail.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController implements Serializable {
    private static final long serialVersionUID = -9112847279985188090L;

    private final IUserService service;
    private final ModelMapper mapper;

    @Autowired
    public UserController(IUserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO dto) {
        String TAG = dto.getName();
        log.info(" Criando um usuário: " + dto.getName());

       User user = service.createUser(dto);
       return mapper.map(user, UserDTO.class);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findById(@PathVariable Long id) {
        log.info("Obtendo um livro pelo ID: " + id);
        User user = service.findById(id).orElseThrow(() -> {
            log.info("Usuário não encontrado pelo id: " + id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        return userDTO;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDTO> findAll(UserDTO dto, Pageable pageable) {
        log.info("Listando usuarios");
       return service.find(dto, pageable).map(entity ->  mapper.map(entity, UserDTO.class));
    }
}
