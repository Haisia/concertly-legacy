package com.concertly.concertly_legacy.domain.reservation.dto;

import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseSeatDto;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseReservationDtoBuilder {
  private final Reservation reservation;
  private BaseReservationDto target;

  public BaseReservationDtoBuilder withConcert() {
    target.setConcert(BaseConcertDto.from(reservation.getSeat().getConcert()));
    return this;
  }

  public BaseReservationDtoBuilder withSeat() {
    target.setSeat(BaseSeatDto.from(reservation.getSeat()));
    return this;
  }

  public BaseReservationDtoBuilder withUser() {
    target.setUser(BaseUserDto.from(reservation.getUser()));
    return this;
  }

  public static BaseReservationDtoBuilder builder(Reservation reservation) {
    BaseReservationDtoBuilder builder = new BaseReservationDtoBuilder(reservation);
    builder.target = BaseReservationDto.from(reservation);
    return builder;
  }

  public BaseReservationDto build() {
    return target;
  }

}
