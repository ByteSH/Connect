package com.example.CONNECT.controller;

import com.example.CONNECT.entry.User;
import com.example.CONNECT.exception.WeatherServiceException;
import com.example.CONNECT.repository.UserRepositoryIMPL;
import com.example.CONNECT.service.UserDetailsServiceIMPL;
import com.example.CONNECT.service.UserService;
import com.example.CONNECT.service.WeatherService;
import com.example.CONNECT.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    public static final String HEALTH_CHECK = "OK";

    @Autowired
    public UserService userService;

    @Autowired
    private UserRepositoryIMPL userRepositoryIMPL;

    Integer temperature;
    List<String > weather_description;

    @Autowired
    public WeatherService weatherService;



    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceIMPL userDetailsServiceIMPL;
    @Autowired
    private JwtUtil jwtUtil;


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





    @GetMapping("/weather-check/{city}")
    public ResponseEntity<?> weatherCheck(@PathVariable String city){
        try{
            temperature = weatherService.getWeather(city).getCurrent().getTemperature();
            weather_description = weatherService.getWeather(city).getCurrent().getWeatherDescriptions();
            return new ResponseEntity<>(temperature+" "+weather_description,HttpStatus.OK);
        } catch (WeatherServiceException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsServiceIMPL.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);


        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username and password", HttpStatus.BAD_REQUEST);

        }
    }
}