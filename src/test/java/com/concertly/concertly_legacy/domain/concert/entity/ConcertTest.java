package com.concertly.concertly_legacy.domain.concert.entity;

import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertCommentRepository;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import jakarta.persistence.EntityManager;
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
public class ConcertTest {

  @Autowired private ConcertRepository concertRepository;
  @Autowired private ConcertCommentRepository commentRepository;
  @Autowired private EntityManager em;

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

    for (int i = 1; i <= 30; i++) {
      String seatNumber = "A" + i;
      Long price = 10000L;
      concert.createSeat(seatNumber, price);
    }
  }

  @Test
  public void isAlreadyExistSeat() {
    assertTrue(concert.isAlreadyExistSeat("A1"));
    assertFalse(concert.isAlreadyExistSeat("Z1"));
  }

  @Test
  public void createComment() {
    concert.createComment("댓글달기");
    em.flush();
    em.clear();

    assertEquals(1, concert.getCommentList().size());

    ConcertComment concertComment = concert.getCommentList().get(0);
    assertEquals("댓글달기", concertComment.getComment());

    ConcertComment foundComment = commentRepository.findById(concertComment.getId()).get();
    assertEquals(concertComment.getComment(), foundComment.getComment());
  }

  @Test
  public void getAllSeatList() {
    assertEquals(30, concert.getSeatList().size());
  }

  @Test
  public void findSeatBySeatNumber() {
    assertEquals("A1", concert.findSeatBySeatNumber("A1").getSeatNumber());
    assertThrows(NotFoundException.class, () -> concert.findSeatBySeatNumber("Z1"));
  }

  @Test
  public void findAvailableSeatList() {
    assertEquals(30, concert.findAvailableSeatList().size());

    concert.findAvailableSeatList().get(0).reservation();
    assertEquals(29, concert.findAvailableSeatList().size());
  }

  @Test
  public void isReservationAvailable_예약가능시간이아닐경우() {
    assertTrue(concert.isReservationAvailable());
    concert.setStartTime(LocalDateTime.now().plusHours(1));
    assertFalse(concert.isReservationAvailable());
  }

  @Test
  public void isReservationAvailable_예약가능좌석이없을경우() {
    assertTrue(concert.isReservationAvailable());
    concert.findAvailableSeatList().forEach(Seat::reservation);
    assertFalse(concert.isReservationAvailable());
  }
}
