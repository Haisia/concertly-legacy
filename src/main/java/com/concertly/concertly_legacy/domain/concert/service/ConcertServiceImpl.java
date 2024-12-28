package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.commons.exceptions.PermissionDeniedException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.ConcertComment;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertCommentRepository;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.web.concert.ConcertApiMapper;
import com.concertly.concertly_legacy.web.concert.dto.*;
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
  private final ConcertCommentRepository concertCommentRepository;

  @Transactional
  public UUID create(CreateConcertRequest request, UUID requesterId) {
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

    Concert savedConcert = concertRepository.save(concert);
    log.info("{}님이 {} 콘서트와 좌석을 생성하였습니다.", requesterId, concert.getId());

    return savedConcert.getId();
  }

  @Transactional
  public UUID createComment(CreateConcertCommentRequest request, UUID requesterId) {
    Concert concert = concertRepository.findById(request.getConcertId())
      .orElseThrow(() -> new NotFoundException("Concert", "id", request.getConcertId().toString()));

    ConcertComment comment = concert.createComment(request.getComment());
    concertCommentRepository.save(comment);
    log.info("{} 님이 {} 에 {} 댓글을 달았습니다.", requesterId, request.getConcertId(), comment.getId());
    return comment.getId();
  }

  @Transactional
  public void deleteComment(DeleteConcertCommentRequest request, UUID requesterId) {
    ConcertComment concertComment = concertCommentRepository.findById(request.getCommentId())
      .orElseThrow(() -> new NotFoundException("ConcertComment", "id", request.getCommentId().toString()));
    if (!concertComment.getCreatedBy().equals(requesterId.toString())) {
      throw new PermissionDeniedException();
    }

    concertCommentRepository.deleteById(request.getCommentId());
    log.info("{} 님이 {} 댓글을 삭제하였습니다.", requesterId, request.getCommentId());
  }

  @Transactional
  public FetchReservableConcertSeatsResponse fetchReservableSeats(FetchReservableConcertSeatsRequest request) {
    Concert concert = concertRepository.findById(request.getConcertId())
      .orElseThrow(() -> new NotFoundException("Concert", "id", request.getConcertId().toString()));

    return ConcertApiMapper.toFetchReservableConcertSeatsResponse(concert);
  }
}
