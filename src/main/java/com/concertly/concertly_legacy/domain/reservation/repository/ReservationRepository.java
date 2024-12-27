package com.concertly.concertly_legacy.domain.reservation.repository;

import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
