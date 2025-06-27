package org.uberprojectauthservice.services;

import org.springframework.stereotype.Service;
import org.uberprojectauthservice.dtos.PassengerDTO;
import org.uberprojectauthservice.dtos.PassengerSignupRequestDTO;
import org.uberprojectauthservice.models.Passenger;
import org.uberprojectauthservice.repositories.PassengerRepository;

@Service
public class AuthService {
    private final PassengerRepository passengerRepository;

    public AuthService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public PassengerDTO signupPassenger(PassengerSignupRequestDTO passengerSignupRequestDTO){
        Passenger passenger=Passenger.builder()
                .email(passengerSignupRequestDTO.getEmail())
                .name(passengerSignupRequestDTO.getName())
                .phoneNumber(passengerSignupRequestDTO.getPhoneNumber())
                .password(passengerSignupRequestDTO.getPassword()) //ToDO we have to use bcrypt to encrypt the password
                .build();
        Passenger newPassenger=passengerRepository.save(passenger);

        return PassengerDTO.from(newPassenger);

    }
}
