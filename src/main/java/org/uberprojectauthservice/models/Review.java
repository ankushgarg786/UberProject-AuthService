package org.uberprojectauthservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "booking_review")//for giving custom name to the table using @Table annotation so that it will change name at the database only, we have option in @Entity also, but then we have to refer that name in the whole code
@Inheritance(strategy = InheritanceType.JOINED)
public class Review extends BaseModel{

    @Column(nullable = false)
    private String content;

    private Double rating;

    @OneToOne
    @JoinColumn(name = "booking_id",referencedColumnName ="id" )
    private Booking booking;

}