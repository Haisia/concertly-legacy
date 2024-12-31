package com.concertly.concertly_legacy.web.reservation.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.reservation.dto.BaseReservationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "콘서트 예약 응답 Dto")
@AllArgsConstructor
@Data
public class ReservationConcertResponse extends BaseDto {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  public UUID reservationId;

  public String concertTitle;
  public String location;
  public LocalDateTime startTime;
  public LocalDateTime endTime;

  public String seatNumber;
  public Long price;

  public String buyerEmail;

  public static ReservationConcertResponse from(BaseReservationDto dto) {
    ReservationConcertResponse result = ReservationConcertResponse.fromBase(dto, ReservationConcertResponse.class);
    result.setReservationId(dto.getId());
    result.setConcertTitle(dto.getConcert().getTitle());
    result.setLocation(dto.getConcert().getLocation());
    result.setStartTime(dto.getConcert().getStartTime());
    result.setEndTime(dto.getConcert().getEndTime());
    result.setSeatNumber(dto.getSeat().getSeatNumber());
    result.setPrice(dto.getSeat().getPrice());
    result.setBuyerEmail(dto.getUser().getEmail());
    return result;
  }
}
