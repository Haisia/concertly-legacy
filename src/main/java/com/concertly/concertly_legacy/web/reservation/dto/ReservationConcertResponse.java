package com.concertly.concertly_legacy.web.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class ReservationConcertResponse {
  public UUID reservationId;
}
