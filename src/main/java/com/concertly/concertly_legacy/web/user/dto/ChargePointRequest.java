package com.concertly.concertly_legacy.web.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@AllArgsConstructor @NoArgsConstructor
@Schema(description = "포인트 충전 요청 Dto")
@Data
public class ChargePointRequest {

  @Schema(description = USER_POINT_SCHEMA_DESCRIPTION, example = USER_POINT_SCHEMA_EXAMPLE)
  @Min(value = 1, message = USER_POINT_PATTERN_MESSAGE)
  public Long point;
}
