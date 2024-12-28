package com.concertly.concertly_legacy.web.reservation;

import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import com.concertly.concertly_legacy.domain.reservation.service.ReservationService;
import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.ReservationConcertResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping("/reserve-concert")
  public ResponseEntity<ReservationConcertResponse> reserveConcert(@RequestBody ReserveConcertRequest request,
                                                                   @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    return ResponseEntity.ok().body(reservationService.concertReservation(request, requesterId));
  }

  @PostMapping("/cancel")
  public void cancelReservation(@RequestBody CancelReservationRequest request,
                                @AuthenticationPrincipal ConcertlyUserDetail userDetail){
    UUID requesterId = userDetail.getUser().getId();
    reservationService.cancelReservation(request, requesterId);
  }

}
