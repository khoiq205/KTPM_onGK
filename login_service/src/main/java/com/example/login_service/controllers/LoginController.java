package com.example.login_service.controllers;

import com.example.login_service.models.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class LoginController {
    @GetMapping("/check-user")
    public User checkUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8081/checkUser?username=" + username + "&password=" + password;
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {
                }
        );
        return responseEntity.getBody();
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8081/checkUser?username=" + username + "&password=" + password;
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<User>() {
                }
        );
        User user = responseEntity.getBody();
        if(user !=null)
            return "Đăng nhập thành công";
        else return "Đăng nhập thất bại";
    }
}