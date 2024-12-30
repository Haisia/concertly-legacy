package com.concertly.concertly_legacy.domain.concert.repository;

import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcSeatRepositoryImpl implements JdbcSeatRepository {

  private final JdbcTemplate jdbcTemplate;

  @Transactional
  @Override
  public void saveAll(List<Seat> seatList) {
    String sql =
      "insert into seats (id, concert_id, seat_number, price, deleted, created_by, created_at)" +
        " values (?, ?, ?, ?, ?, ?, ?)";
    List<Object[]> args = seatList.stream().map(s -> new Object[]{
      s.getId()
      , s.getConcert().getId()
      , s.getSeatNumber()
      , s.getPrice()
      , s.getDeleted()
      , s.getCreatedBy()
      , s.getCreatedAt()
    }).toList();

    jdbcTemplate.batchUpdate(sql, args);

  }
}
