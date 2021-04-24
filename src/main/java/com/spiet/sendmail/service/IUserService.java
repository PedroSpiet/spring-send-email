package com.spiet.sendmail.service;

import com.spiet.sendmail.DTOs.UserDTO;
import com.spiet.sendmail.domain.User;

public interface IUserService {
    User createUser(UserDTO user);
}
