package com.concertly.concertly_legacy.domain.user.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseUserDto extends BaseDto {
  public String email;
  public String password;
  public Long point;
}
