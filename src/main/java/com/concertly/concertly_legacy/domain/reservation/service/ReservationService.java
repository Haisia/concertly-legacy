package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.ReservationConcertResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;

import java.util.UUID;

public interface ReservationService {

  ReservationConcertResponse concertReservation(ReserveConcertRequest request, UUID requesterId);

  void cancelReservation(CancelReservationRequest request, UUID requesterId);
}
