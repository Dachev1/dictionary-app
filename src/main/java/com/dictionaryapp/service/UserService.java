package com.dictionaryapp.service;

import com.dictionaryapp.controller.dto.UserRegisterDTO;

public interface UserService {
    boolean isUsernameTaken(String username);
    void register(UserRegisterDTO userRegisterDTO);
}
