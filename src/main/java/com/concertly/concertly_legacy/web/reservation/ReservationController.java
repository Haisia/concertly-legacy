package com.concertly.concertly_legacy.web.reservation;

import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import com.concertly.concertly_legacy.domain.reservation.service.ReservationService;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import lombok.RequiredArgsConstructor;
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
  public void reserveConcert(@RequestBody ReserveConcertRequest request,
                              @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    reservationService.concertReservation(request, requesterId);
  }

}
