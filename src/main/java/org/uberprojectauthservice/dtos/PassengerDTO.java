package org.uberprojectauthservice.dtos;

import lombok.*;
import org.uberprojectauthservice.models.Passenger;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private LocalDateTime createdAt;

    public static PassengerDTO from(Passenger passenger) {
        PassengerDTO result = PassengerDTO.builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .phoneNumber(passenger.getPhoneNumber())
                .createdAt(passenger.getCreatedAt())
                .password(passenger.getPassword())
                .build();
        return result;
    }
}
