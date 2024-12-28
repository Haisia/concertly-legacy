package com.concertly.concertly_legacy.web.reservation;

import com.concertly.concertly_legacy.commons.annotations.NeedLogin;
import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import com.concertly.concertly_legacy.domain.reservation.service.ReservationService;
import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.FetchOwnReservationResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReservationConcertResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

  private final ReservationService reservationService;

  @NeedLogin
  @PostMapping("/reserve-concert")
  public ResponseEntity<ReservationConcertResponse> reserveConcert(@Valid @RequestBody ReserveConcertRequest request,
                                                                    @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    return ResponseEntity.ok().body(reservationService.concertReservation(request, requesterId));
  }

  @NeedLogin
  @PostMapping("/cancel")
  public ResponseEntity<?> cancelReservation(@Valid @RequestBody CancelReservationRequest request,
                                             @AuthenticationPrincipal ConcertlyUserDetail userDetail){
    UUID requesterId = userDetail.getUser().getId();
    reservationService.cancelReservation(request, requesterId);
    return ResponseEntity.ok().build();
  }

  @NeedLogin
  @PostMapping("/fetch-owns")
  public ResponseEntity<List<FetchOwnReservationResponse>> fetchReservations(@AuthenticationPrincipal ConcertlyUserDetail userDetail){
    UUID requesterId = userDetail.getUser().getId();
    return ResponseEntity.ok().body(reservationService.fetchOwns(requesterId));
  }

}
