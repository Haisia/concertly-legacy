package com.concertly.concertly_legacy.domain.user.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseUserDto extends BaseDto {
  public String email;
  public String password;
  public Long point;

  public static BaseUserDto from(User user) {
    BaseUserDto result = BaseDto.fromBase(user, BaseUserDto.class);
    result.setEmail(user.getEmail());
    result.setPassword(user.getPassword());
    result.setPoint(user.currentPoints());
    return result;
  }
}
