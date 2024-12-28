package com.concertly.concertly_legacy.web.reservation.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReserveConcertRequest {
  public UUID concertId;
  public String seatNumber;
}
