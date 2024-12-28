package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.web.concert.dto.*;

import java.util.List;
import java.util.UUID;

public interface ConcertService {

  UUID create(CreateConcertRequest request, UUID requesterId);

  UUID createComment(CreateConcertCommentRequest request, UUID requesterId);

  void deleteComment(DeleteConcertCommentRequest request, UUID requesterId);

  FetchReservableConcertSeatsResponse fetchReservableSeats(FetchReservableConcertSeatsRequest request);

  List<FetchReservableConcertSeatsResponse> fetchReservableConcerts();

}
