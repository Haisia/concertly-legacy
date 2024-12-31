package com.concertly.concertly_legacy.domain.reservation.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseReservationDto extends BaseDto {
  public UUID seatId;
  public UUID userId;

  public static BaseReservationDto from(Reservation reservation) {
    BaseReservationDto dto = (BaseReservationDto) BaseReservationDto.fromBase(reservation);
    dto.setSeatId(reservation.getSeat().getId());
    dto.setUserId(reservation.getUser().getId());
    return dto;
  }
}
