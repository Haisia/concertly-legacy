package com.concertly.concertly_legacy.web.concert;

import com.concertly.concertly_legacy.commons.annotations.NeedLogin;
import com.concertly.concertly_legacy.config.jwt.ConcertlyUserDetail;
import com.concertly.concertly_legacy.domain.concert.service.ConcertService;
import com.concertly.concertly_legacy.web.concert.dto.*;
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
@RequestMapping("/api/concert")
public class ConcertController {

  private final ConcertService concertService;

  @NeedLogin
  @PostMapping("/create")
  public ResponseEntity<?> create(@Valid @RequestBody CreateConcertRequest request,
                                  @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    concertService.create(request, requesterId);
    return ResponseEntity.ok().build();
  }

  @NeedLogin
  @PostMapping("/write-comment")
  public ResponseEntity<?> writeComment(@Valid @RequestBody CreateConcertCommentRequest request,
                                        @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    concertService.createComment(request, requesterId);
    return ResponseEntity.ok().build();
  }

  @NeedLogin
  @PostMapping("/delete-comment")
  public ResponseEntity<?> deleteComment(@Valid @RequestBody DeleteConcertCommentRequest request,
                                        @AuthenticationPrincipal ConcertlyUserDetail userDetail) {
    UUID requesterId = userDetail.getUser().getId();
    concertService.deleteComment(request, requesterId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/fetch-reservable-seats")
  public ResponseEntity<FetchReservableConcertSeatsResponse> fetchReservableSeats(@Valid @RequestBody FetchReservableConcertSeatsRequest request){
    return ResponseEntity.ok().body(concertService.fetchReservableSeats(request));
  }

  @PostMapping("/fetch-reservable-concerts")
  public ResponseEntity<List<FetchReservableConcertSeatsResponse>> fetchReservableConcerts() {
    return ResponseEntity.ok().body(concertService.fetchReservableConcerts());

  }
}
