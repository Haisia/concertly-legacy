package com.concertly.concertly_legacy.domain.reservation.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseSeatDto;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseReservationDto extends BaseDto {
  public BaseConcertDto concert;
  public BaseSeatDto seat;
  public BaseUserDto user;

  public static BaseReservationDto from(Reservation reservation, boolean withConcert ,boolean withSeat, boolean withUser) {
    BaseReservationDto dto = BaseReservationDto.fromBase(reservation, BaseReservationDto.class);
    if (withConcert) dto.setConcert(BaseConcertDto.from(reservation.getSeat().getConcert(), false, false));
    if (withSeat) dto.setSeat(BaseSeatDto.from(reservation.getSeat()));
    if (withUser) dto.setUser(BaseUserDto.from(reservation.getUser()));
    return dto;
  }

  public static List<BaseReservationDto> from(List<Reservation> reservations, boolean withConcert ,boolean withSeat, boolean withUser) {
    return reservations.stream()
      .map(reservation -> BaseReservationDto.from(reservation, withConcert, withSeat, withUser))
      .toList()
      ;
  }
}
