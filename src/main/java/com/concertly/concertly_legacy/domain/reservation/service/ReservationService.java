package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.web.reservation.dto.ConcertReservationRequest;

import java.util.UUID;

public interface ReservationService {

  void concertReservation(ConcertReservationRequest request, UUID requesterId);

}
