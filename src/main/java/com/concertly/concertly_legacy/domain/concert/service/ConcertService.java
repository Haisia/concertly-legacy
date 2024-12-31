package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertCommentDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.web.concert.dto.*;

import java.util.List;
import java.util.UUID;

public interface ConcertService {

  BaseConcertDto create(CreateConcertRequest request, UUID requesterId);

  List<Seat> saveSeatList(CreateConcertRequest request, Concert concert);

  BaseConcertCommentDto createComment(CreateConcertCommentRequest request, UUID requesterId);

  UUID deleteComment(DeleteConcertCommentRequest request, UUID requesterId);

  BaseConcertDto fetchReservableSeats(FetchReservableConcertSeatsRequest request);

  List<BaseConcertDto> fetchReservableConcerts();

}
