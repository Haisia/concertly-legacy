package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.FetchOwnReservationResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReservationConcertResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

  ReservationConcertResponse concertReservation(ReserveConcertRequest request, UUID requesterId);

  void cancelReservation(CancelReservationRequest request, UUID requesterId);

  List<FetchOwnReservationResponse> fetchOwns(UUID requesterId);
}
