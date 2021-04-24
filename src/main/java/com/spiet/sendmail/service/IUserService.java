package com.spiet.sendmail.service;

import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface IUserService {
    User createUser(UserDTO user);
    Optional<User> findById(Long id);
}
