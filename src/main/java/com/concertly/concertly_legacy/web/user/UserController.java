package com.concertly.concertly_legacy.web.user;

import com.concertly.concertly_legacy.config.jwt.JwtUtil;
import com.concertly.concertly_legacy.commons.exceptions.LoginFailException;
import com.concertly.concertly_legacy.domain.user.service.UserService;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import com.concertly.concertly_legacy.web.user.dto.LoginRequest;
import com.concertly.concertly_legacy.web.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  private final UserService userService;

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody CreateUserRequest request) {
    userService.create(request);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    } catch (AuthenticationException e) {
      throw new LoginFailException(request.getEmail());
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

    String generatedToken = jwtUtil.generateToken(userDetails.getUsername());
    return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(generatedToken));
  }

}
