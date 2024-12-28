package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.ConcertComment;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertCommentRequest;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {

  private final ConcertRepository concertRepository;

  @Transactional
  public void create(CreateConcertRequest request, UUID requesterId) {
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
    log.info("{}님이 {} 콘서트와 좌석을 생성하였습니다.", requesterId, concert.getId());
  }

  @Transactional
  public void createComment(CreateConcertCommentRequest request, UUID requesterId) {
    Concert concert = concertRepository.findById(request.getConcertId())
      .orElseThrow(() -> new NotFoundException("Concert", "id", request.getConcertId().toString()));

    ConcertComment comment = concert.createComment(request.getComment());
    log.info("{} 님이 {} 에 {} 댓글을 달았습니다.", requesterId, request.getConcertId(), comment.getId());
  }

}
