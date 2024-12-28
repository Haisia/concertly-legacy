package com.concertly.concertly_legacy.web.concert.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class FetchReservableConcertSeatsResponse {
  public String concertName;
  public LocalDateTime concertStartTime;
  public LocalDateTime concertEndTime;

  public List<ReservableSeat> reservableSeats;

  @Builder
  @Data
  public static class ReservableSeat {
    public String seatNumber;
    public Long price;
  }
}
