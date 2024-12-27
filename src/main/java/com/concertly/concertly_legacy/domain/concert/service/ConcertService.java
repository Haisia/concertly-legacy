package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;

public interface ConcertService {

  void create(CreateConcertRequest request);

}
