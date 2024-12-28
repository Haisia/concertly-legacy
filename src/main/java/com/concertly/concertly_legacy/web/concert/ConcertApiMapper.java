package com.concertly.concertly_legacy.web.concert;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.web.concert.dto.FetchReservableConcertSeatsResponse;

import java.util.List;

public class ConcertApiMapper {

  public static FetchReservableConcertSeatsResponse toFetchReservableConcertSeatsResponse(Concert concert) {
    FetchReservableConcertSeatsResponse response = FetchReservableConcertSeatsResponse.builder()
      .concertName(concert.getTitle())
      .concertStartTime(concert.getStartTime())
      .concertEndTime(concert.getEndTime())
      .build();

    List<Seat> availableSeatList = concert.findAvailableSeatList();
    response.setReservableSeats(toReservableSeatList(availableSeatList));
    return response;
  }

  public static FetchReservableConcertSeatsResponse.ReservableSeat toReservableSeat(Seat seat) {
    return FetchReservableConcertSeatsResponse.ReservableSeat.builder()
      .seatNumber(seat.getSeatNumber())
      .price(seat.getPrice())
      .build()
      ;
  }

  public static List<FetchReservableConcertSeatsResponse.ReservableSeat> toReservableSeatList(List<Seat> seatList) {
    return seatList.stream()
      .map(ConcertApiMapper::toReservableSeat)
      .toList();
  }

}
