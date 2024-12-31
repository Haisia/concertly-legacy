package com.concertly.concertly_legacy.domain.reservation.dto;

import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseSeatDto;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import com.concertly.concertly_legacy.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseReservationDtoBuilder {
  private final Reservation reservation;
  private BaseReservationDto target;

  public BaseReservationDtoBuilder withConcert() {
    Seat seat = reservation.getSeat();
    if (seat == null) throw new IllegalArgumentException("seat 를 찾을 수 없습니다.");
    if (seat.getConcert() == null) throw new IllegalArgumentException("concert 를 찾을 수 없습니다.");
    target.setConcert(BaseConcertDto.from(seat.getConcert()));
    return this;
  }

  public BaseReservationDtoBuilder withSeat() {
    Seat seat = reservation.getSeat();
    if (seat == null) throw new IllegalArgumentException("seat 를 찾을 수 없습니다.");
    target.setSeat(BaseSeatDto.from(seat));
    return this;
  }

  public BaseReservationDtoBuilder withUser() {
    User user = reservation.getUser();
    if (user == null) throw new IllegalArgumentException("user 를 찾을 수 없습니다.");
    target.setUser(BaseUserDto.from(user));
    return this;
  }

  public static BaseReservationDtoBuilder builder(Reservation reservation) {
    if (reservation == null) throw new IllegalArgumentException("Reservation 은 null 이면 안됩니다.");
    BaseReservationDtoBuilder builder = new BaseReservationDtoBuilder(reservation);
    builder.target = BaseReservationDto.from(reservation);
    return builder;
  }

  public BaseReservationDto build() {
    return target;
  }

}
