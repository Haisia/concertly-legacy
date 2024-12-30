package com.concertly.concertly_legacy.domain.reservation.entity;

import com.concertly.concertly_legacy.commons.exceptions.UnableStatusException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
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
public class ReservationTest {

  @Autowired private ConcertRepository concertRepository;
  @Autowired private UserRepository userRepository;

  Concert concert;
  User user;

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

    User user = User.of("test@test.com", "123123");
    user.chargePoints(100000L);
    this.user = userRepository.save(user);
  }

  @Test
  public void reserve() {
    Seat seat = concert.findSeatBySeatNumber("A1");
    Reservation.reserve(seat, user);
  }

  @Test
  public void reserve_공연시작시간임박으로인한예약실패() {
    concert.setStartTime(LocalDateTime.now().plusHours(1));
    assertThrows(UnableStatusException.class, () -> Reservation.reserve(concert.findAvailableSeatList().get(0), user));
  }

  @Test
  public void cancel() {
    Seat seat = concert.findAvailableSeatList().get(0);
    Reservation reserve = Reservation.reserve(seat, user);

    assertTrue(user.currentPoints() < 100000L);
    reserve.cancel();

    assertEquals(100000L, user.currentPoints());
    assertEquals("Y", reserve.getDeleted());
  }

  @Test
  public void cancel_이미취소된예약을반복해서취소하면실패() {
    Seat seat = concert.findAvailableSeatList().get(0);
    Reservation reserve = Reservation.reserve(seat, user);
    reserve.cancel();

    assertThrows(UnableStatusException.class, reserve::cancel);
  }

  @Test
  public void cancel_예약취소는공연시작24시간전까지만가능() {
    Seat seat = concert.findAvailableSeatList().get(0);
    Reservation reserve = Reservation.reserve(seat, user);

    concert.setStartTime(LocalDateTime.now().minusHours(1));

    assertThrows(UnableStatusException.class, reserve::cancel);
  }

  @Test
  public void isOwner() {
    Seat seat = concert.findAvailableSeatList().get(0);
    Reservation reserve = Reservation.reserve(seat, user);

    assertTrue(reserve.isOwner(user.getId()));
  }

}
