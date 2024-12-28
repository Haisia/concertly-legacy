package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;

import java.util.UUID;

public interface ReservationService {

  void concertReservation(ReserveConcertRequest request, UUID requesterId);

}
