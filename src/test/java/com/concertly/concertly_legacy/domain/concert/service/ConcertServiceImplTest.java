package com.concertly.concertly_legacy.domain.concert.service;

import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertCommentRepository;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.concert.repository.JdbcSeatRepository;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.smaple.ConcertSamples;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertCommentRequest;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import com.concertly.concertly_legacy.web.concert.dto.FetchReservableConcertSeatsRequest;
import com.concertly.concertly_legacy.web.concert.dto.FetchReservableConcertSeatsResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConcertServiceImplTest {

  private AutoCloseable mocks;
  @InjectMocks private ConcertServiceImpl concertService;

  @Mock private ConcertRepository concertRepository;
  @Mock private ConcertCommentRepository commentRepository;
  @Mock private JdbcSeatRepository seatRepository;
  @Mock private EntityManager em;

  Concert concert;
  User user;
  Seat seat;

  @BeforeEach
  void beforeEach(){
    mocks = MockitoAnnotations.openMocks(this);

    this.concert = Concert.builder()
      .title("콘서트제목")
      .location("일산킨텍스")
      .startTime(LocalDateTime.now().plusYears(10))
      .endTime(LocalDateTime.now().plusYears(10).plusHours(2))
      .build();
    this.seat = concert.createSeat("A1", 10000L);

    this.user = User.of("test@test.com", "123123");
    user.setId(UUID.randomUUID());
    user.chargePoints(100000L);
  }

  @AfterEach
  void tearDown() throws Exception {
    if (mocks != null) {
      mocks.close();
    }
  }

  @Test
  public void create() {
    CreateConcertRequest request = ConcertSamples.createConcertRequest();
    concert.setId(UUID.randomUUID());

    when(concertRepository.save(any())).thenReturn(concert);
    UUID createdConcertId = concertService.create(request, UUID.randomUUID());

    assertEquals(concert.getId(), createdConcertId);
  }

  @Test
  public void saveSeatList() {
    CreateConcertRequest request = ConcertSamples.createConcertRequest();

    List<Seat> seatList = concertService.saveSeatList(request, concert);

    Long size = request.getSeatList().stream()
      .map(CreateConcertRequest.Seats::getSeatMaxLineNumber)
      .reduce(0L, Long::sum);

    assertEquals(size, seatList.size());
    verify(seatRepository, times(1)).saveAll(anyList());

  }

  @Test
  public void createComment() {
    CreateConcertCommentRequest request = ConcertSamples.createConcertCommentRequest();
    UUID requesterId = UUID.randomUUID();

    when(concertRepository.findById(request.getConcertId())).thenReturn(Optional.of(concert));
    concertService.createComment(request, requesterId);

    verify(concertRepository, times(1)).findById(request.getConcertId());
    verify(commentRepository, times(1)).save(any());
  }

  @Test
  public void createComment_존재하지않는콘서트에댓글작성요청시예외발생() {
    CreateConcertCommentRequest request = ConcertSamples.createConcertCommentRequest();
    UUID requesterId = UUID.randomUUID();

    assertThrows(NotFoundException.class, () -> concertService.createComment(request, requesterId));
  }

  @Test
  public void fetchReservableSeats() {
    FetchReservableConcertSeatsRequest request = ConcertSamples.fetchReservableConcertSeatsRequest();
    when(concertRepository.findById(request.getConcertId())).thenReturn(Optional.of(concert));

    FetchReservableConcertSeatsResponse response = concertService.fetchReservableSeats(request);
    assertEquals(concert.findAvailableSeatList().size(), response.getReservableSeats().size());
  }

  @Test
  public void fetchReservableSeats_존재하지않는콘서트조회요청시예외발생() throws Exception {
    FetchReservableConcertSeatsRequest request = ConcertSamples.fetchReservableConcertSeatsRequest();
    assertThrows(NotFoundException.class, () -> concertService.fetchReservableSeats(request));
  }

  @Test
  public void fetchReservableConcerts() {
    when(concertRepository.findAll()).thenReturn(List.of(concert));
    List<FetchReservableConcertSeatsResponse> response = concertService.fetchReservableConcerts();
    assertEquals(concert.findAvailableSeatList().size(), response.size());
  }
}