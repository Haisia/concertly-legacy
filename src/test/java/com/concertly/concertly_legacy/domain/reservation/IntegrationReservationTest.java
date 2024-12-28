package com.concertly.concertly_legacy.domain.reservation;

import com.concertly.concertly_legacy.commons.exceptions.UnableStatusException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.concert.service.ConcertService;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.reservation.repository.ReservationRepository;
import com.concertly.concertly_legacy.domain.reservation.service.ReservationService;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.domain.user.service.UserService;
import com.concertly.concertly_legacy.smaple.ConcertSamples;
import com.concertly.concertly_legacy.smaple.UserSamples;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import com.concertly.concertly_legacy.web.reservation.dto.FetchOwnReservationResponse;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import com.concertly.concertly_legacy.web.user.dto.ChargePointRequest;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class IntegrationReservationTest {

  @Autowired private ReservationService reservationService;
  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private ConcertService concertService;
  @Autowired private ConcertRepository concertRepository;
  @Autowired
  private ReservationRepository reservationRepository;

  @Test
  public void 예약_시_포인트가_차감되어야_한다_취소시_환불되어야_한다() {
    //given
    User user = createUser();
    Concert concert = createConcert();

    userService.chargePoint(new ChargePointRequest(100000L), user.getId());

    //when & then
    assertThat(user.currentPoints()).isEqualTo(100000L);
    ReserveConcertRequest request = new ReserveConcertRequest(concert.getId(), "A1");
    UUID reservationId = reservationService.concertReservation(request, user.getId()).getReservationId();
    assertThat(user.currentPoints()).isLessThan(100000L);

    reservationRepository.findById(reservationId).get().cancel();
    assertThat(user.currentPoints()).isEqualTo(100000L);
  }

  @Test
  public void 예약_내역을_조회할_수_있어야_한다() {
    //given
    User user = createUser();
    userService.chargePoint(new ChargePointRequest(100000L), user.getId());
    Concert concert = createConcert();
    ReserveConcertRequest request = new ReserveConcertRequest(concert.getId(), "A1");
    UUID reservationId = reservationService.concertReservation(request, user.getId()).getReservationId();

    //when
    List<FetchOwnReservationResponse> responseList = reservationService.fetchOwns(user.getId());

    //then
    assertEquals(1, responseList.size());
    FetchOwnReservationResponse response = responseList.get(0);
    assertEquals(reservationId, response.getReservationId());
    assertEquals("A1", response.getSeatNumber());
    assertEquals(ConcertSamples.createConcertRequest().getSeatList().get(0).getPrice(), response.getPrice());
  }

  @Test
  public void 예약_취소는_공연_시작_24시간_전까지만_가능하다() {
    //given
    User user = createUser();
    Concert concert = createConcert();

    //when
    userService.chargePoint(new ChargePointRequest(100000L), user.getId());
    ReserveConcertRequest request = new ReserveConcertRequest(concert.getId(), "A1");
    UUID reservationId = reservationService.concertReservation(request, user.getId()).getReservationId();
    Reservation reservation = reservationRepository.findById(reservationId).get();
    concert.setStartTime(LocalDateTime.now().plusHours(3));
    concertRepository.save(concert);

    //then
    assertThrows(UnableStatusException.class, reservation::cancel);
  }

  @Test
  public void 이미_예약된_좌석은_예약할_수_없다() {
    //given
    User user = createUser();
    Concert concert = createConcert();

    //when
    userService.chargePoint(new ChargePointRequest(100000L), user.getId());
    ReserveConcertRequest request = new ReserveConcertRequest(concert.getId(), "A1");
    UUID reservationId = reservationService.concertReservation(request, user.getId()).getReservationId();
    Reservation reservation = reservationRepository.findById(reservationId).get();

    //then
    assertThrows(UnableStatusException.class, () -> reservationService.concertReservation(request, user.getId()));
  }

  private User createUser() {
    CreateUserRequest createUserRequest = UserSamples.createUserRequest();
    String email = userService.create(createUserRequest);

    return userRepository.findByEmail(email).get();
  }

  private Concert createConcert() {
    CreateConcertRequest concertRequest = ConcertSamples.createConcertRequest();
    UUID concertId = concertService.create(concertRequest, UUID.randomUUID());

    return concertRepository.findById(concertId).get();
  }
}
