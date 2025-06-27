package org.uberprojectauthservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uberprojectauthservice.models.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}
