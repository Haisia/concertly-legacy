package com.concertly.concertly_legacy.smaple;

import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;

import java.util.UUID;

public class ReservationSamples {

  public static ReserveConcertRequest reserveConcertRequest() {
    return new ReserveConcertRequest(UUID.randomUUID(), "A1");
  }

  public static CancelReservationRequest cancelReservationRequest() {
    CancelReservationRequest cancelReservationRequest = new CancelReservationRequest();
    cancelReservationRequest.setReservationId(UUID.randomUUID());
    return cancelReservationRequest;
  }

}
