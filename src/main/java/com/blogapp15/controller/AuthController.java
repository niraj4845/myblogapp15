package com.blogapp15.controller;

import com.blogapp15.entity.User;
import com.blogapp15.payload.LoginDto;
import com.blogapp15.payload.Signup;
import com.blogapp15.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
@PostMapping("/sign-up")
public ResponseEntity<String> createUser( @RequestBody Signup signup){
        if (userRepository.existsByEmail(signup.getEmail())){
            return new ResponseEntity<>("email present", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if (userRepository.existsByUsername(signup.getUsername())){
            return new ResponseEntity<>("user present",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user=new User();
        user.setName(signup.getName());
        user.setUsername(signup.getUsername());
        user.setEmail(signup.getEmail());
        user.setPassword(passwordEncoder.encode(signup.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Registerde",HttpStatus.CREATED);
    }
    @PostMapping("sign-in")
    public  ResponseEntity<String> signIn(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("sIGN-IN",HttpStatus.OK);
    }

}
