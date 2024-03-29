package com.example.login_service.controllers;

import com.example.login_service.models.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping("/login")
public class LoginController {
    @GetMapping("/check-user")
    public User checkUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:8081/register/checkUser?username=" + username + "&password=" + password;
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
    public String login(@RequestParam("username") String username, @RequestParam("password") String password)  {
        User user = checkUser(username,password);
        if(user !=null)
            return "Đăng nhập thành công";
        else return "Đăng nhập thất bại";
    }
    @PostMapping("/save")
    public String saveUser(@RequestBody User user){
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl ="http://localhost:8081/register/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> httpEntity = new HttpEntity<>(user,headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<User>() {});
        if(responseEntity.getBody()== null)
            return "Lưu thất bại";
        else return "Lưu thành công";
    }
}