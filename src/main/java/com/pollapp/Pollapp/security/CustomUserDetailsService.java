package com.pollapp.Pollapp.security;

//loading a user’s data given its username

import com.pollapp.Pollapp.model.User;
import com.pollapp.Pollapp.repositiory.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
//    method used will be used by spring security
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }

//    method used by JWTAuthenticationFilter
    @Transactional

    public  UserDetails loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException("User with id : " + id  + " Not Found")
        );
        return UserPrincipal.create(user);
    }
}
