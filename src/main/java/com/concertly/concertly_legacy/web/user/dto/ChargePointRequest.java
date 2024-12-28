package com.concertly.concertly_legacy.web.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@Schema(description = "포인트 충전 요청 Dto")
@Data
public class ChargePointRequest {

  @Schema(description = USER_POINT_SCHEMA_DESCRIPTION, example = USER_POINT_SCHEMA_EXAMPLE)
  @Pattern(regexp = USER_POINT_PATTERN_REGEX, message = USER_POINT_PATTERN_MESSAGE)
  public Long point;
}
