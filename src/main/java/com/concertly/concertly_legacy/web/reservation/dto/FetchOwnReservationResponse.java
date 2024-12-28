package com.concertly.concertly_legacy.web.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class FetchOwnReservationResponse {

  public UUID reservationId;
  public UUID seatId;
  public String concertName;
  public String seatNumber;
  public Long price;

  public LocalDateTime concertStartTime;
  public LocalDateTime concertEndTime;
  public LocalDateTime reservedAt;
}
