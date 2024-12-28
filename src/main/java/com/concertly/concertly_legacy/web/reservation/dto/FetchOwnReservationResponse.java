package com.concertly.concertly_legacy.web.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@Schema(description = "자신의 예약한 예약정보 Dto")
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class FetchOwnReservationResponse {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  public UUID reservationId;

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  public UUID seatId;

  @Schema(description = CONCERT_TITLE_SCHEMA_DESCRIPTION, example = CONCERT_TITLE_SCHEMA_EXAMPLE)
  public String concertName;

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
}
