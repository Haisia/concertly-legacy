package com.concertly.concertly_legacy.web.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.USER_EMAIL_PATTERN_MESSAGE;

@Schema(description = "유저 회원가입 요청 Dto")
@Data
public class LoginRequest {

  @Schema(description = USER_EMAIL_SCHEMA_DESCRIPTION, example = USER_EMAIL_SCHEMA_EXAMPLE)
  @Pattern(regexp = USER_EMAIL_PATTERN_REGEX, message = USER_EMAIL_PATTERN_MESSAGE)
  public String email;

  @Schema(description = USER_PASSWORD_SCHEMA_DESCRIPTION, example = USER_PASSWORD_SCHEMA_EXAMPLE)
  @Pattern(regexp = USER_PASSWORD_PATTERN_REGEX, message = USER_PASSWORD_PATTERN_MESSAGE)
  public String password;

}
