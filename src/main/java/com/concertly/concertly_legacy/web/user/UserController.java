package com.concertly.concertly_legacy.web.user;

import com.concertly.concertly_legacy.commons.annotations.NeedLogin;
import com.concertly.concertly_legacy.commons.dto.BaseResponse;
import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import com.concertly.concertly_legacy.config.jwt.JwtUtil;
import com.concertly.concertly_legacy.commons.exceptions.LoginFailException;
import com.concertly.concertly_legacy.domain.user.dto.BaseUserDto;
import com.concertly.concertly_legacy.domain.user.service.UserService;
import com.concertly.concertly_legacy.web.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "유저 API", description = "유저 관련 REST API 를 제공합니다.")
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  private final UserService userService;

  @Operation(summary = "회원가입을 위한 컨트롤러.", description = "회원가입을 제공합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "회원가입에 성공했을 경우."),
  })
  @PostMapping("/create")
  public BaseResponse<CreateUserResponse> create(@Valid @RequestBody CreateUserRequest request) {
    BaseUserDto result = userService.create(request);
    return new BaseResponse<>(CreateUserResponse.from(result));
  }

  @Operation(summary = "로그인을 위한 컨트롤러", description = "로그인을 제공합니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "로그인에 성공했을 경우."),
  })
  @GetMapping("/login")
  public BaseResponse<?> login(@Valid @RequestBody LoginRequest request) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    } catch (AuthenticationException e) {
      throw new LoginFailException("로그인에 실패했습니다.");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

    String generatedToken = jwtUtil.generateToken(userDetails.getUsername());
    return new BaseResponse<>(new LoginResponse(generatedToken));
  }

  @Operation(summary = "포인트 충전 컨트롤러.", description = "포인트 충전을 위한 테스트 컨트롤러 입니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "포인트 충전에 성공했을 경우."),
  })
  @NeedLogin
  @PostMapping("/charge-point")
  public BaseResponse<?> chargePoint(@Valid @RequestBody ChargePointRequest request,
                                        @AuthenticationPrincipal ConcertlyUserDetail userDetails){
    UUID requesterId = userDetails.getUser().getId();
    userService.chargePoint(request, requesterId);
    return new BaseResponse<>("포인트 충전에 성공했습니다.");
  }

}
