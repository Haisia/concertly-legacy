package com.concertly.concertly_legacy.web.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@Schema(description = "콘서트 예약 응답 Dto")
@AllArgsConstructor
@Data
public class ReservationConcertResponse {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  public UUID reservationId;
}
