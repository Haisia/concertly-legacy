package com.concertly.concertly_legacy.domain.concert.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.commons.enums.SeatStatus;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseSeatDto extends BaseDto {
  public UUID concertId;
  public String seatNumber;
  public SeatStatus status;
  public Long price;
  public UUID reservationId;

  public static BaseSeatDto from(Seat seat) {
    return getBaseDto(seat);
  }

  public static List<BaseSeatDto> from(List<Seat> seats) {
    return seats.stream()
      .map(BaseSeatDto::from)
      .toList()
      ;
  }

  private static BaseSeatDto getBaseDto(Seat seat) {
    BaseSeatDto baseDto = BaseSeatDto.fromBase(seat, BaseSeatDto.class);
    baseDto.setConcertId(seat.getConcert().getId());
    baseDto.setSeatNumber(seat.getSeatNumber());
    baseDto.setStatus(seat.getStatus());
    baseDto.setPrice(seat.getPrice());
    if(seat.getReservation() != null) baseDto.setReservationId(seat.getReservation().getId());
    return baseDto;
  }
}