package com.concertly.concertly_legacy.domain.user.service;

import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public void create(CreateUserRequest request) {
    User user = User.of(request.getEmail(), passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
  }

}
