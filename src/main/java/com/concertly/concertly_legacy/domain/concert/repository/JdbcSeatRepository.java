package com.concertly.concertly_legacy.domain.concert.repository;

import com.concertly.concertly_legacy.domain.concert.entity.Seat;

import java.util.List;

public interface JdbcSeatRepository {
  void saveAll(List<Seat> seat);
}
