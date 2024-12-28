package com.concertly.concertly_legacy.web.concert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.ID_PATTERN_MESSAGE;

@Schema(description = "예약가능한 콘서트 좌석 요청 Dto")
@AllArgsConstructor @NoArgsConstructor
@Data
public class FetchReservableConcertSeatsRequest {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  @Pattern(regexp = ID_PATTERN_REGEX, message = ID_PATTERN_MESSAGE)
  public UUID concertId;
}
