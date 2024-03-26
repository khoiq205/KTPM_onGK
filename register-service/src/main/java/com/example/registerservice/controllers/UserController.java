package com.example.registerservice.controllers;

import com.example.registerservice.models.User;
import com.example.registerservice.repositories.UserRepository;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @GetMapping("/getId")
    public ResponseEntity<User> getUserById(@RequestParam("id") Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(userOptional.get());
    }

    @GetMapping("/checkUser")
    public User checkUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userRepository.findByUserNameAndPassword(username, password);
        return user;
    }
}
