package com.concertly.concertly_legacy.web.reservation.dto;

import lombok.Data;

@Data
public class ConcertReservationRequest {
  public Long concertId;
  public String seatNumber;
}
