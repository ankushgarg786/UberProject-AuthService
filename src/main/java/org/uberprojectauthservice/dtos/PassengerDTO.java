package org.uberprojectauthservice.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private String id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private LocalDateTime createdAt;
}
