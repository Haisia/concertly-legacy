package com.concertly.concertly_legacy.web.reservation.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CancelReservationRequest {
  public UUID reservationId;
}
