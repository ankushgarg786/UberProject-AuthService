package org.uberprojectauthservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uberprojectauthservice.dtos.PassengerSignupRequestDTO;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @PostMapping("/signup/passenger")
    public ResponseEntity<?>signUp(@RequestBody PassengerSignupRequestDTO passengerSignupRequestDTO)
    {

        return null;
    }
    @PostMapping("/signup/driver")
    public ResponseEntity<?>signUp()
    {

        return null;
    }
}
