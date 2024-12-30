package com.concertly.concertly_legacy.domain.concert.entity;

import com.concertly.concertly_legacy.commons.exceptions.UnableStatusException;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class SeatTest {

  @Autowired private ConcertRepository concertRepository;

  Concert concert;

  @BeforeEach
  void beforeEach(){
    Concert concert = Concert.builder()
      .title("콘서트제목")
      .location("일산킨텍스")
      .startTime(LocalDateTime.now().plusYears(10))
      .endTime(LocalDateTime.now().plusYears(10).plusHours(2))
      .build();
    this.concert = concertRepository.save(concert);

    concert.createSeat("A1", 10000L);
  }

  @Test
  public void isAvailable() {
    Seat seat = concert.findAvailableSeatList().get(0);
    assertTrue(seat.isAvailable());

    seat.reservation();
    assertFalse(seat.isAvailable());

    assertThrows(UnableStatusException.class, seat::reservation);
  }

  @Test
  public void cancel() {
    Seat seat = concert.findAvailableSeatList().get(0);

    assertThrows(UnableStatusException.class, seat::cancel);

    seat.reservation();
    assertDoesNotThrow(seat::cancel);
  }

}
