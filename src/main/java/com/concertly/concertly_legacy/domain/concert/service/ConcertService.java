package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.web.concert.dto.CreateConcertCommentRequest;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;

import java.util.UUID;

public interface ConcertService {

  void create(CreateConcertRequest request, UUID requesterId);

  void createComment(CreateConcertCommentRequest request, UUID requesterId);

}
