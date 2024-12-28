package com.dictionaryapp.service;

import com.dictionaryapp.controller.dto.user.UserRegisterDTO;

public interface UserService {
    boolean isUsernameTaken(String username);
    void register(UserRegisterDTO userRegisterDTO);
    boolean authenticate(String username, String password);
    Long getUserIdByUsername(String username);
}
