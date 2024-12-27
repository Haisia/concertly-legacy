package com.concertly.concertly_legacy.web.user;

import com.concertly.concertly_legacy.domain.user.service.UserService;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

  private final UserService userService;

  @PostMapping("/user/create")
  public ResponseEntity<?> create(@RequestBody CreateUserRequest request) {
    userService.create(request);
    return ResponseEntity.ok().build();
  }

}
