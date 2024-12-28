package com.concertly.concertly_legacy.web.concert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.ID_PATTERN_MESSAGE;

@Schema(description = "예약 가능한 콘서트 좌석 응답 Dto")
@Builder
@Data
public class FetchReservableConcertSeatsResponse {

  @Schema(description = CONCERT_TITLE_SCHEMA_DESCRIPTION, example = CONCERT_TITLE_SCHEMA_EXAMPLE)
  public String concertName;

  @Schema(description = CONCERT_START_TIME_SCHEMA_DESCRIPTION, example = CONCERT_START_TIME_SCHEMA_EXAMPLE)
  public LocalDateTime concertStartTime;

  @Schema(description = CONCERT_END_TIME_SCHEMA_DESCRIPTION, example = CONCERT_END_TIME_SCHEMA_EXAMPLE)
  public LocalDateTime concertEndTime;

  @Schema(description = "예약가능한 좌석 정보")
  public List<ReservableSeat> reservableSeats;

  @Builder
  @Data
  public static class ReservableSeat {

    @Schema(description = CONCERT_SEAT_NUMBER_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_NUMBER_SCHEMA_EXAMPLE)
    public String seatNumber;

    @Schema(description = CONCERT_SEAT_PRICE_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_PRICE_SCHEMA_EXAMPLE)
    public Long price;
  }
}
