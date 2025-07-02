package com.example.CONNECT.controller;

import com.example.CONNECT.entry.User;
import com.example.CONNECT.repository.UserRepositoryIMPL;
import com.example.CONNECT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/public")
public class PublicController {

    public static final String HEALTH_CHECK = "OK";

    @Autowired
    public UserService userService;

    @Autowired
    private UserRepositoryIMPL userRepositoryIMPL;



    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>(HEALTH_CHECK,HttpStatus.OK);
    }



    @PostMapping("/signup")
    public ResponseEntity<?> createNewAccount(@RequestBody User user){
        try {
            return new ResponseEntity<>(userService.createNewAccount(user),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get-email")
    public List<User> getSentiment(){
        return userRepositoryIMPL.getUserForSA();
    }
}