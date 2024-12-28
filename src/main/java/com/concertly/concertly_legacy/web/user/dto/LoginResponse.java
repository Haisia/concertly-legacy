package com.concertly.concertly_legacy.web.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.USER_EMAIL_PATTERN_MESSAGE;

@Schema(description = "로그인 응답 JWT 토큰 Dto")
@AllArgsConstructor
@Data
public class LoginResponse {

  @Schema(description = JWT_SCHEMA_DESCRIPTION, example = JWT_SCHEMA_EXAMPLE)
  public String token;
}
