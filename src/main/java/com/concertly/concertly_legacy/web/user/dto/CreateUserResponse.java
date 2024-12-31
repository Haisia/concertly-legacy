package com.concertly.concertly_legacy.web.user.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserResponse extends BaseDto {
  public String email;

  public static CreateUserResponse from(BaseUserDto dto) {
    CreateUserResponse response = CreateUserResponse.fromBase(dto, CreateUserResponse.class);
    response.setEmail(dto.getEmail());
    return response;
  }
}
