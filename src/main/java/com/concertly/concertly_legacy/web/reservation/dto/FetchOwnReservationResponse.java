package com.concertly.concertly_legacy.web.reservation.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.reservation.dto.BaseReservationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "자신의 예약한 예약정보 Dto")
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class FetchOwnReservationResponse extends BaseDto {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  public UUID reservationId;

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  public UUID seatId;

  @Schema(description = CONCERT_TITLE_SCHEMA_DESCRIPTION, example = CONCERT_TITLE_SCHEMA_EXAMPLE)
  public String concertTitle;

  @Schema(description = CONCERT_SEAT_NUMBER_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_NUMBER_SCHEMA_EXAMPLE)
  public String seatNumber;

  @Schema(description = CONCERT_SEAT_PRICE_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_PRICE_SCHEMA_EXAMPLE)
  public Long price;

  @Schema(description = CONCERT_START_TIME_SCHEMA_DESCRIPTION, example = CONCERT_START_TIME_SCHEMA_EXAMPLE)
  public LocalDateTime concertStartTime;

  @Schema(description = CONCERT_END_TIME_SCHEMA_DESCRIPTION, example = CONCERT_END_TIME_SCHEMA_EXAMPLE)
  public LocalDateTime concertEndTime;

  @Schema(description = RESERVATION_AT_SCHEMA_DESCRIPTION, example = RESERVATION_AT_SCHEMA_EXAMPLE)
  public LocalDateTime reservedAt;

  public static FetchOwnReservationResponse from(BaseReservationDto dto) {
    FetchOwnReservationResponse result = FetchOwnReservationResponse.fromBase(dto, FetchOwnReservationResponse.class);
    result.setReservationId(dto.getId());
    result.setSeatId(dto.getSeat().getId());
    result.setConcertTitle(dto.getConcert().getTitle());
    result.setSeatNumber(dto.getSeat().getSeatNumber());
    result.setPrice(dto.getSeat().getPrice());
    result.setConcertStartTime(dto.getConcert().getStartTime());
    result.setConcertEndTime(dto.getConcert().getEndTime());
    result.setReservedAt(dto.getCreatedAt());
    return result;
  }

  public static List<FetchOwnReservationResponse> from(List<BaseReservationDto> dtoList) {
    return dtoList.stream()
      .map(FetchOwnReservationResponse::from)
      .toList()
      ;
  }
}
