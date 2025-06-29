package org.uberprojectauthservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.uberprojectauthservice.helpers.AuthPassengerDetails;
import org.uberprojectauthservice.models.Passenger;
import org.uberprojectauthservice.repositories.PassengerRepository;

import java.util.Optional;

/**
 * This class is responsible for loading the user in the form of UserDetails object for Auth
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private  PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Passenger> passenger=passengerRepository.findPassengerByEmail(email);
        if(passenger.isPresent()) {
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("Cannot find passenger with email: " + email);
        }
    }
}
