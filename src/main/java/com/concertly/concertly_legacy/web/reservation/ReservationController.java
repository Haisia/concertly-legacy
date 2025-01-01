package com.concertly.concertly_legacy.web.reservation;

import com.concertly.concertly_legacy.commons.annotations.NeedLogin;
import com.concertly.concertly_legacy.commons.dto.BaseResponse;
import com.concertly.concertly_legacy.commons.exceptions.ErrorResponseDto;
import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import com.concertly.concertly_legacy.domain.reservation.dto.BaseReservationDto;
import com.concertly.concertly_legacy.domain.reservation.service.ReservationService;
import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.FetchOwnReservationResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReservationConcertResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "예약 API", description = "예약 관련 REST API 를 제공합니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

  private final ReservationService reservationService;

  @Operation(summary = "콘서트를 예약합니다.", description = "콘서트를 예약합니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "콘서트 예약에 성공했을 경우"),
    @ApiResponse(responseCode = "400", description = "보유 포인트가 콘서트 좌석의 비용보다 적은경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "404", description = "콘서트가 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "404", description = "일치하는 좌석번호가 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "409", description = "이미 예약된 좌석을 예약 시도할 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "409", description = "공연 시작까지 24시간 미만 남은 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @NeedLogin
  @PostMapping("/reserve-concert")
  public BaseResponse<ReservationConcertResponse> reserveConcert(@Valid @RequestBody ReserveConcertRequest request,
                                                                  @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    BaseReservationDto result = reservationService.concertReservation(request, requesterId);
    return new BaseResponse<>(ReservationConcertResponse.from(result));
  }

  @Operation(summary = "콘서트 예약을 취소합니다.", description = "콘서트 예약을 취소합니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "콘서트 예약 취소에 성공했을 경우."),
    @ApiResponse(responseCode = "404", description = "예약이 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "403", description = "일치하는 좌석번호가 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "409", description = "이미 예약된 좌석을 예약 시도할 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
  })
  @NeedLogin
  @PostMapping("/cancel")
  public BaseResponse<?> cancelReservation(@Valid @RequestBody CancelReservationRequest request,
                                              @AuthenticationPrincipal ConcertlyUserDetail userDetail){
    UUID requesterId = userDetail.getUser().getId();
    reservationService.cancelReservation(request, requesterId);
    return new BaseResponse<>("삭제가 완료되었습니다.");
  }

  @Operation(summary = "자신의 콘서트 예약 기록을 확인하는 컨트롤러.", description = "자신의 콘서트 예약 기록을 확인합니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "콘서트 조회에 성공했을 겨우."),
    @ApiResponse(responseCode = "404", description = "콘서트가 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
  })
  @NeedLogin
  @GetMapping("/fetch-owns")
  public BaseResponse<List<FetchOwnReservationResponse>> fetchReservations(@AuthenticationPrincipal ConcertlyUserDetail userDetail){
    UUID requesterId = userDetail.getUser().getId();
    List<BaseReservationDto> results = reservationService.fetchOwns(requesterId);
    return new BaseResponse<>(FetchOwnReservationResponse.from(results));
  }

}
