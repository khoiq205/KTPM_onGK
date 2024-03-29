package com.example.registerservice.controllers;

import com.example.registerservice.models.User;
import com.example.registerservice.repositories.UserRepository;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@RestController
@RequestMapping("/register")
public class UserController {
    @Autowired
    UserRepository userRepository;
    Jedis jedis = new Jedis();

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        String key = String.valueOf(user.getUserId());
        jedis.hset(key,"username",user.getUserName());
        jedis.hset(key,"password",user.getPassword());
        jedis.hset(key,"email",user.getEmail());
        userRepository.save(user);
        return user;
    }

    @GetMapping("/getId")
    public ResponseEntity<User> getUserById(@RequestParam("id") Long id) {
        String key = String.valueOf(id);
        if(jedis.exists(key)){
            User userInCache = new User();
            userInCache.setUserId(id);
            userInCache.setUserName(jedis.hget(key,"username"));
            userInCache.setPassword(jedis.hget(key,"password"));
            userInCache.setEmail(jedis.hget(key,"email"));
            return  ResponseEntity.ok(userInCache);
        }
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
