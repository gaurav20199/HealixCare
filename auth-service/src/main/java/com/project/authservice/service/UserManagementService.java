package com.project.authservice.service;

import com.project.authservice.entity.User;
import com.project.authservice.repository.UserRepository;
import com.project.authservice.security.usermanagement.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserManagementService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        return user.map(SecurityUser::new).orElseThrow(()-> new UsernameNotFoundException("User with username don't exist in thee system"));
    }

}
