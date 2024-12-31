package com.concertly.concertly_legacy.web.concert;

import com.concertly.concertly_legacy.commons.annotations.NeedLogin;
import com.concertly.concertly_legacy.commons.dto.BaseResponse;
import com.concertly.concertly_legacy.commons.exceptions.ErrorResponseDto;
import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertCommentDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.service.ConcertService;
import com.concertly.concertly_legacy.web.concert.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "콘서트 API", description = "콘서트 관련 REST API 를 제공합니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concert")
public class ConcertController {

  private final ConcertService concertService;

  @Operation(summary = "콘서트 생성 컨트롤러", description = "콘서트를 생성합니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "콘서트 생성에 성공했을 경우"),
    @ApiResponse(responseCode = "409", description = "중복된 좌석을 생성하려 할 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @NeedLogin
  @PostMapping("/create")
  public BaseResponse<CreateConcertResponse> createConcert(@Valid @RequestBody CreateConcertRequest request,
                                          @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    BaseConcertDto result = concertService.create(request, requesterId);
    return new BaseResponse<>(CreateConcertResponse.from(result));
  }

  @Operation(summary = "콘서트 댓글작성 컨트롤러", description = "콘서트에 댓글을 작성합니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "콘서트 댓글 작성에 성공했을 경우"),
    @ApiResponse(responseCode = "404", description = "콘서트가 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @NeedLogin
  @PostMapping("/create-comment")
  public BaseResponse<CreateConcertCommentResponse> createConcertCommentRequest(@Valid @RequestBody CreateConcertCommentRequest request,
                                                        @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    BaseConcertCommentDto result = concertService.createComment(request, requesterId);
    return new BaseResponse<>(CreateConcertCommentResponse.from(result));
  }

  @Operation(summary = "콘서트 댓글삭제 컨트롤러", description = "콘서트에 댓글을 삭제합니다.",
    security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "콘서트 댓글 삭제에 성공했을 경우"),
    @ApiResponse(responseCode = "403", description = "삭제하려는 댓글이 자신이 작성한 댓글이 아닌 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "404", description = "콘서트가 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),

  })
  @NeedLogin
  @DeleteMapping("/delete-comment")
  public BaseResponse<?> deleteComment(@Valid @RequestBody DeleteConcertCommentRequest request,
                                        @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    concertService.deleteComment(request, requesterId);
    return new BaseResponse<>("삭제 완료");
  }

  @Operation(summary = "예약가능한 좌석 조회 컨트롤러", description = "예약가능한 좌석을 조회합니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "조회에 성공한 경우"),
    @ApiResponse(responseCode = "404", description = "콘서트가 존재하지 않을 경우",
      content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/fetch-reservable-seats")
  public BaseResponse<FetchReservableConcertSeatsResponse> fetchReservableSeats(@Valid @RequestBody FetchReservableConcertSeatsRequest request){
    BaseConcertDto result = concertService.fetchReservableSeats(request);
    return new BaseResponse<>(FetchReservableConcertSeatsResponse.from(result));
  }

  @Operation(summary = "예약가능한 콘서트 조회 컨트롤러", description = "예약가능한 콘서트의 정보를 조회힙니다.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "조회에 성공한 경우"),
  })
  @GetMapping("/fetch-reservable-concerts")
  public BaseResponse<List<FetchReservableConcertSeatsResponse>> fetchReservableConcerts() {
    List<BaseConcertDto> result = concertService.fetchReservableConcerts();
    return new BaseResponse<>(FetchReservableConcertSeatsResponse.from(result));

  }
}
