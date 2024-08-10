package com.sheryians.major.service;

import com.sheryians.major.model.User;
import com.sheryians.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
