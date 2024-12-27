package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {

  private final ConcertRepository concertRepository;

  @Transactional
  public void create(CreateConcertRequest request) {
    Concert concert = Concert.builder()
      .title(request.getTitle())
      .location(request.getLocation())
      .startTime(request.getStartTime())
      .endTime(request.getEndTime())
      .build();

    for (CreateConcertRequest.Seats seat : request.getSeatList()) {
      for (int i = 1; i <= seat.seatMaxLineNumber; i++) {
        String seatNumber =  seat.getSeatLabel().toUpperCase() + i;
        concert.createSeat(seatNumber, seat.getPrice());
      }
    }

    concertRepository.save(concert);
  }

}
