package com.concertly.concertly_legacy.web.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.ID_PATTERN_MESSAGE;

@Schema(description = "콘서트 예약 요청 Dto")
@AllArgsConstructor @NoArgsConstructor
@Data
public class ReserveConcertRequest {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  @Pattern(regexp = ID_PATTERN_REGEX, message = ID_PATTERN_MESSAGE)
  public UUID concertId;

  @Schema(description = CONCERT_SEAT_NUMBER_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_NUMBER_SCHEMA_EXAMPLE)
  @Pattern(regexp = CONCERT_SEAT_NUMBER_PATTERN_REGEX, message = CONCERT_SEAT_NUMBER_PATTERN_MESSAGE)
  public String seatNumber;
}
