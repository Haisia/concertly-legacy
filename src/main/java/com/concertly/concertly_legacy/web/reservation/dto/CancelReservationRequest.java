package com.concertly.concertly_legacy.web.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.USER_EMAIL_PATTERN_MESSAGE;

@Schema(description = "예약 취소 요청 Dto")
@Data
public class CancelReservationRequest {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  @Pattern(regexp = ID_PATTERN_REGEX, message = ID_PATTERN_MESSAGE)
  public UUID reservationId;
}
