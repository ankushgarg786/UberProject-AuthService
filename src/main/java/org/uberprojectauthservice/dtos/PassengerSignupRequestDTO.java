package org.uberprojectauthservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequestDTO {

    private String name;

    private String email;

    private String password;

    private String phoneNumber;
}
