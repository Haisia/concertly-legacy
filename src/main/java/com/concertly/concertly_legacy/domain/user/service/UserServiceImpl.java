package com.concertly.concertly_legacy.domain.user.service;

import com.concertly.concertly_legacy.commons.exceptions.AlreadyExistException;
import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.web.user.dto.ChargePointRequest;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public String create(CreateUserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new AlreadyExistException("이미 사용 중인 이메일입니다.");
    }

    User user = User.of(request.getEmail(), passwordEncoder.encode(request.getPassword()));
    User createdUser = userRepository.save(user);
    log.info("{} 유저가 회원가입했습니다.", createdUser.getEmail());

    return createdUser.getEmail();
  }

  @Transactional
  @Override
  public void chargePoint(ChargePointRequest request, UUID requesterId) {
    User user = userRepository.findById(requesterId)
      .orElseThrow(() -> new NotFoundException("User", "id", requesterId.toString()));
    user.chargePoints(request.getPoint());
    log.info("{} 님의 포인트를 {} 만큼 충전했습니다.", requesterId, request.getPoint());
  }

}
