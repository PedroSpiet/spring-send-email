package com.spiet.sendmail.service;

import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface IUserService {
    User createUser(UserDTO user);
    Optional<User> findById(Long id);
    Page<User> find(UserDTO userDTO, Pageable pageable);
}
