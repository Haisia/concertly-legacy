package com.concertly.concertly_legacy.web.concert;

import com.concertly.concertly_legacy.commons.annotations.NeedLogin;
import com.concertly.concertly_legacy.domain.concert.service.ConcertService;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concert")
public class ConcertController {

  private final ConcertService concertService;

  @NeedLogin
  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody CreateConcertRequest request) {
    concertService.create(request);
    return ResponseEntity.ok().build();
  }

}
