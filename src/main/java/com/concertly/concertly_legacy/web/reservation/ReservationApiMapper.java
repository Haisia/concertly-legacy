package com.concertly.concertly_legacy.web.reservation;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.web.reservation.dto.FetchOwnReservationResponse;

import java.util.List;

public class ReservationApiMapper {

  public static FetchOwnReservationResponse toFetchOwnReservationResponse(Reservation reservation) {
    Seat seat = reservation.getSeat();
    Concert concert = seat.getConcert();

    return FetchOwnReservationResponse.builder()
      .reservationId(reservation.getId())
      .seatId(seat.getId())
      .concertName(concert.getTitle())
      .seatNumber(seat.getSeatNumber())
      .price(seat.getPrice())
      .concertStartTime(concert.getStartTime())
      .concertEndTime(concert.getEndTime())
      .reservedAt(reservation.getCreatedAt())
      .build();
  }

  public static List<FetchOwnReservationResponse> toFetchOwnReservationResponse(List<Reservation> reservations) {
    return reservations.stream()
      .map(ReservationApiMapper::toFetchOwnReservationResponse)
      .toList();
  }


}
