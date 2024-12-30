package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.commons.enums.SeatStatus;
import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.commons.exceptions.PermissionDeniedException;
import com.concertly.concertly_legacy.commons.exceptions.UnableStatusException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.reservation.repository.ReservationRepository;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.smaple.ReservationSamples;
import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.FetchOwnReservationResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationServiceImplTest {

  private AutoCloseable mocks;
  @InjectMocks private ReservationServiceImpl reservationService;

  @Mock private ConcertRepository concertRepository;
  @Mock private ReservationRepository reservationRepository;
  @Mock private UserRepository userRepository;

  Concert concert;
  User user;
  Seat seat;

  @BeforeEach
  void beforeEach() {
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
  public void concertReservation() {
    UUID requesterId = UUID.randomUUID();
    ReserveConcertRequest request = ReservationSamples.reserveConcertRequest();

    when(concertRepository.findWithSeatsById(request.getConcertId())).thenReturn(Optional.of(concert));
    when(userRepository.findById(requesterId)).thenReturn(Optional.of(user));

    Reservation reserve = Reservation.reserve(seat, user);
    seat.setStatus(SeatStatus.AVAILABLE);
    UUID reserveId = UUID.randomUUID();
    reserve.setId(reserveId);

    when(reservationRepository.save(any())).thenReturn(reserve);

    assertEquals(reserveId, reservationService.concertReservation(request, requesterId).getReservationId());
    verify(concertRepository, times(1)).findWithSeatsById(request.getConcertId());
    verify(userRepository, times(1)).findById(requesterId);
    verify(reservationRepository, times(1)).save(any());
  }

  @Test
  public void concertReservation_예약할수없는좌석요청시예외발생() {
    when(concertRepository.findWithSeatsById(any())).thenReturn(Optional.of(concert));
    seat.setStatus(SeatStatus.RESERVED);

    assertThrows(UnableStatusException.class,
      () -> reservationService.concertReservation(ReservationSamples.reserveConcertRequest(), UUID.randomUUID())
    );
  }

  @Test
  public void concertReservation_잘못된유저의요청일경우예외발생() {
    when(concertRepository.findWithSeatsById(any())).thenReturn(Optional.of(concert));
    user.setId(UUID.randomUUID());

    assertThrows(NotFoundException.class,
      () -> reservationService.concertReservation(ReservationSamples.reserveConcertRequest(), UUID.randomUUID())
      );
  }

  @Test
  public void cancelReservation() {
    UUID requesterId = user.getId();
    CancelReservationRequest request = ReservationSamples.cancelReservationRequest();
    Reservation reserve = Reservation.reserve(seat, user);

    when(reservationRepository.findById(request.getReservationId())).thenReturn(Optional.of(reserve));

    seat.setStatus(SeatStatus.RESERVED);
    reservationService.cancelReservation(request, requesterId);
    verify(reservationRepository, times(1)).findById(request.getReservationId());
  }

  @Test
  public void cancelReservation_없는예약취소요청시예외발생() {
    CancelReservationRequest request = ReservationSamples.cancelReservationRequest();

    assertThrows(NotFoundException.class, () -> reservationService.cancelReservation(request, UUID.randomUUID()));
  }

  @Test
  public void cancelReservation_예약당사자가아닌사람이예약취소시도시예외발생() {
    CancelReservationRequest request = ReservationSamples.cancelReservationRequest();
    Reservation reserve = Reservation.reserve(seat, user);
    when(reservationRepository.findById(request.getReservationId())).thenReturn(Optional.of(reserve));

    assertThrows(PermissionDeniedException.class, () -> reservationService.cancelReservation(request, UUID.randomUUID()));
  }

  @Test
  public void fetchOwns() {
    Reservation.reserve(seat, user);
    UUID requesterId = user.getId();
    when(userRepository.findById(requesterId)).thenReturn(Optional.of(user));
    FetchOwnReservationResponse response = reservationService.fetchOwns(requesterId).get(0);

    assertEquals(concert.getTitle(), response.getConcertName());
    assertEquals(seat.getSeatNumber(), response.getSeatNumber());
    verify(userRepository, times(1)).findById(requesterId);
  }

}