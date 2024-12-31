package com.concertly.concertly_legacy.domain.reservation.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseSeatDto;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseReservationDto extends BaseDto {
  public BaseConcertDto concert;
  public BaseSeatDto seat;
  public BaseUserDto user;

  public static BaseReservationDto from(Reservation reservation) {
    return BaseReservationDto.fromBase(reservation, BaseReservationDto.class);
  }
}
