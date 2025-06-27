package org.uberprojectauthservice.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.uberprojectauthservice.dtos.PassengerDTO;
import org.uberprojectauthservice.dtos.PassengerSignupRequestDTO;
import org.uberprojectauthservice.models.Passenger;
import org.uberprojectauthservice.repositories.PassengerRepository;

@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PassengerRepository passengerRepository;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder, PassengerRepository passengerRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passengerRepository = passengerRepository;
    }

    public PassengerDTO signupPassenger(PassengerSignupRequestDTO passengerSignupRequestDTO){
        Passenger passenger=Passenger.builder()
                .email(passengerSignupRequestDTO.getEmail())
                .name(passengerSignupRequestDTO.getName())
                .phoneNumber(passengerSignupRequestDTO.getPhoneNumber())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDTO.getPassword())) //ToDO we have to use bcrypt to encrypt the password
                .build();
        Passenger newPassenger=passengerRepository.save(passenger);

        return PassengerDTO.from(newPassenger);

    }
}
