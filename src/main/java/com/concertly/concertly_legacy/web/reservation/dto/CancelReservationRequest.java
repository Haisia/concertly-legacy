package com.concertly.concertly_legacy.web.reservation.dto;

import com.concertly.concertly_legacy.commons.annotations.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@Schema(description = "예약 취소 요청 Dto")
@Data
public class CancelReservationRequest {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  @ValidUUID(message = ID_PATTERN_MESSAGE)
  public UUID reservationId;
}
