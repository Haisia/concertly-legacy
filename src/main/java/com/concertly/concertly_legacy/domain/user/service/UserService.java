package com.concertly.concertly_legacy.domain.user.service;

import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import com.concertly.concertly_legacy.web.user.dto.ChargePointRequest;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;

import java.util.UUID;

public interface UserService {

  BaseUserDto create(CreateUserRequest request);

  void chargePoint(ChargePointRequest request, UUID requesterId);
}
