package com.concertly.concertly_legacy.smaple;

import com.concertly.concertly_legacy.web.user.dto.ChargePointRequest;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;

public class UserSamples {

  public static CreateUserRequest createUserRequest() {
    CreateUserRequest request = new CreateUserRequest();
    request.setEmail("test@test.com");
    request.setPassword("123123");
    return request;
  }

  public static ChargePointRequest chargePointRequest() {
    return new ChargePointRequest(100000L);
  }

}
