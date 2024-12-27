package com.concertly.concertly_legacy.domain.user.service;

import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;

public interface UserService {
  void create(CreateUserRequest request);
}
