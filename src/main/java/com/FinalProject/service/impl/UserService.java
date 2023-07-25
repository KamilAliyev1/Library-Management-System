package com.FinalProject.service.impl;

import com.FinalProject.model.User;
import com.FinalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }
}
