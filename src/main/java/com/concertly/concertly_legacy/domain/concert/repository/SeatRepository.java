package com.concertly.concertly_legacy.domain.concert.repository;

import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
}
