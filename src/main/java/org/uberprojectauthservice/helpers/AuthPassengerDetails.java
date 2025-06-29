package org.uberprojectauthservice.helpers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.uberprojectauthservice.models.Passenger;

import java.util.Collection;
import java.util.List;

// Why we need this class?
// Because security works on UserDetails polymorphic type for Auth
public class AuthPassengerDetails extends Passenger implements UserDetails {

    private String userName;// (email,name ,id)unique identifier in springSecurity it is referred as userName
    private String password;

    public AuthPassengerDetails(Passenger passenger) {
        this.userName=passenger.getEmail();
        this.password=passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
