package com.pollapp.Pollapp.controller;

import com.pollapp.Pollapp.exception.AppException;
import com.pollapp.Pollapp.model.Role;
import com.pollapp.Pollapp.model.RoleName;
import com.pollapp.Pollapp.model.User;
import com.pollapp.Pollapp.payload.ApiResponse;
import com.pollapp.Pollapp.payload.JwtAuthenticationResponse;
import com.pollapp.Pollapp.payload.LoginRequest;
import com.pollapp.Pollapp.payload.SignUpRequest;
import com.pollapp.Pollapp.repositiory.RoleRepository;
import com.pollapp.Pollapp.repositiory.UserRepository;
import com.pollapp.Pollapp.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.Collections;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository ;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return  ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        if(userRepository.existsByUsername(signUpRequest.getUsername())){
            return new ResponseEntity(new ApiResponse(false , "Usernamane is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

//        create user's account
        User user = new User(signUpRequest.getName() , signUpRequest.getUsername(),
                signUpRequest.getEmail() , signUpRequest.getPassword());
//        encrypting the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole =  roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(()-> new AppException("user Role not set."));
        user.setRoles(Collections.singleton(userRole));

        User result =  userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath.path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true , "User registered successfully"));
    }
}
