package com.sheryians.major.service;

import com.sheryians.major.model.CustomUserDetails;
import com.sheryians.major.model.User;
import com.sheryians.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> users  = userRepository.findUserByEmail(s);
        users.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        return users.map(CustomUserDetails::new).get();
    }
}
