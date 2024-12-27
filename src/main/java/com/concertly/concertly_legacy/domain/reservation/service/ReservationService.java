package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.web.reservation.dto.ConcertReservationRequest;

public interface ReservationService {

  void concertReservation(ConcertReservationRequest request, Long requesterId);

}
