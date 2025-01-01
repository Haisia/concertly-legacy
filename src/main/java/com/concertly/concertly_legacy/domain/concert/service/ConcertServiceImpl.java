package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.commons.exceptions.PermissionDeniedException;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDtoBuilder;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertCommentDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.ConcertComment;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertCommentRepository;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.concert.repository.JdbcSeatRepository;
import com.concertly.concertly_legacy.web.concert.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConcertServiceImpl implements ConcertService {

  private final ConcertRepository concertRepository;
  private final ConcertCommentRepository concertCommentRepository;
  private final JdbcSeatRepository jdbcSeatRepository;

  @PersistenceContext
  private final EntityManager em;

  @Transactional
  @Override
  public BaseConcertDto create(CreateConcertRequest request, UUID requesterId) {
    Concert concert = Concert.builder()
      .title(request.getTitle())
      .location(request.getLocation())
      .startTime(request.getStartTime())
      .endTime(request.getEndTime())
      .build();

    Concert savedConcert = concertRepository.save(concert);
    em.flush();
    saveSeatList(request, savedConcert);
    em.merge(savedConcert);

    log.info("{}님이 {} 콘서트와 좌석을 생성하였습니다.", requesterId, savedConcert.getId());
    return BaseConcertDtoBuilder.builder(savedConcert)
      .withSeats()
      .withComments()
      .build();
  }

  @Transactional
  public List<Seat> saveSeatList(CreateConcertRequest request, Concert concert) {
    List<Seat> seatList = new ArrayList<>();
    for (CreateConcertRequest.Seats seat : request.getSeatList()) {
      for (int i = 1; i <= seat.seatMaxLineNumber; i++) {
        String seatNumber = seat.getSeatLabel().toUpperCase() + i;
        seatList.add(Seat.createForJdbc(seatNumber, seat.getPrice(), concert));
      }
    }
    jdbcSeatRepository.saveAll(seatList);
    concert.getAllSeatList().addAll(seatList);

    return seatList;
  }

  @Transactional
  @Override
  public BaseConcertCommentDto createComment(CreateConcertCommentRequest request, UUID requesterId) {
    Concert concert = concertRepository.findById(request.getConcertId())
      .orElseThrow(() -> new NotFoundException("Concert", "id", request.getConcertId().toString()));

    ConcertComment comment = concert.createComment(request.getComment());
    concertCommentRepository.save(comment);
    log.info("{} 님이 {} 에 {} 댓글을 달았습니다.", requesterId, request.getConcertId(), comment.getId());
    return BaseConcertCommentDto.from(comment);
  }

  @Transactional
  @Override
  public UUID deleteComment(DeleteConcertCommentRequest request, UUID requesterId) {
    ConcertComment concertComment = concertCommentRepository.findById(request.getCommentId())
      .orElseThrow(() -> new NotFoundException("ConcertComment", "id", request.getCommentId().toString()));
    if (!concertComment.getCreatedBy().equals(requesterId.toString())) {
      throw new PermissionDeniedException();
    }

    concertCommentRepository.deleteById(request.getCommentId());
    log.info("{} 님이 {} 댓글을 삭제하였습니다.", requesterId, request.getCommentId());
    return request.getCommentId();
  }

  @Transactional
  @Override
  public BaseConcertDto fetchReservableSeats(FetchReservableConcertSeatsRequest request) {
    Concert concert = concertRepository.findById(request.getConcertId())
      .orElseThrow(() -> new NotFoundException("Concert", "id", request.getConcertId().toString()));
    return BaseConcertDtoBuilder.builder(concert)
      .withSeats()
      .build();
  }

  @Transactional
  @Override
  public List<BaseConcertDto> fetchReservableConcerts() {
    return concertRepository.findAll()
      .stream()
      .filter(Concert::isReservationAvailable)
      .map(c -> BaseConcertDtoBuilder.builder(c).withSeats().build())
      .toList()
      ;
  }
}
