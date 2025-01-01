package com.concertly.concertly_legacy.web.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@Schema(description = "로그인 응답 JWT 토큰 Dto")
@AllArgsConstructor
@Data
@JsonPropertyOrder({"token"})
public class LoginResponse {

  @Schema(description = JWT_SCHEMA_DESCRIPTION, example = JWT_SCHEMA_EXAMPLE)
  public String token;
}
