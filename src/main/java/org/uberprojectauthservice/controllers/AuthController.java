package org.uberprojectauthservice.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uberprojectauthservice.dtos.AuthRequestDTO;
import org.uberprojectauthservice.dtos.AuthResponseDTO;
import org.uberprojectauthservice.dtos.PassengerDTO;
import org.uberprojectauthservice.dtos.PassengerSignupRequestDTO;
import org.uberprojectauthservice.services.AuthService;
import org.uberprojectauthservice.services.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDTO passengerSignupRequestDTO) {
        PassengerDTO response = authService.signupPassenger(passengerSignupRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (authRequestDTO.getEmail(), authRequestDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                String jwtToken=jwtService.createToken(authRequestDTO.getEmail());

                //Generating cookie
                ResponseCookie cookie=ResponseCookie.from("JwtToken",jwtToken)
                                .httpOnly(true)
                                .secure(false)
                                .path("/")
                                .maxAge(cookieExpiry)
                                .build();

                response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
                return new ResponseEntity<>(AuthResponseDTO.builder().success(true).build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Auth Not Successful", HttpStatus.UNAUTHORIZED);
            }

        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
