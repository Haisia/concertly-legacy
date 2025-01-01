package com.concertly.concertly_legacy.domain.user.service;

import com.concertly.concertly_legacy.commons.exceptions.AlreadyExistException;
import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.smaple.UserSamples;
import com.concertly.concertly_legacy.web.user.dto.ChargePointRequest;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

  private AutoCloseable mocks;

  @InjectMocks private UserServiceImpl userService;

  @Mock private PasswordEncoder passwordEncoder;
  @Mock private UserRepository userRepository;

  User user;

  @BeforeEach
  void beforeEach() {
    mocks = MockitoAnnotations.openMocks(this);
    this.user = User.of("test@test.com", "123123");
    user.setId(UUID.randomUUID());
    user.chargePoints(100000L);
  }

  @AfterEach
  void tearDown() throws Exception {
    if (mocks != null) {
      mocks.close();
    }
  }

  @Test
  public void create() {
    CreateUserRequest request = UserSamples.createUserRequest();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.save(any())).thenReturn(user);

    String email = userService.create(request).getEmail();
    verify(userRepository, times(1)).existsByEmail(request.getEmail());

    assertEquals(user.getEmail(), email);
  }

  @Test
  public void create_이미존재하는메일로신규가입요청시예외발생() {
    CreateUserRequest request = UserSamples.createUserRequest();

    when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
    assertThrows(AlreadyExistException.class, () -> userService.create(request));
  }

  @Test
  public void chargePoints() {
    ChargePointRequest request = UserSamples.chargePointRequest();
    long prevPoint = user.currentPoints();
    when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

    userService.chargePoint(request, user.getId());

    assertEquals(prevPoint + request.getPoint(), user.currentPoints());
  }

  @Test
  public void chargePoints_없는계정에포인트충전요청시예외발생() {
    ChargePointRequest request = UserSamples.chargePointRequest();
    when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> userService.chargePoint(request, user.getId()));
  }
}
