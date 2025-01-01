package com.concertly.concertly_legacy.domain.user;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.ConcertComment;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertCommentRepository;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.concert.service.ConcertService;
import com.concertly.concertly_legacy.domain.reservation.service.ReservationService;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.domain.user.service.UserService;
import com.concertly.concertly_legacy.smaple.ConcertSamples;
import com.concertly.concertly_legacy.smaple.UserSamples;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertCommentRequest;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import com.concertly.concertly_legacy.web.concert.dto.DeleteConcertCommentRequest;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import com.concertly.concertly_legacy.web.user.dto.ChargePointRequest;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class IntegrationUserTest {

  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private ConcertService concertService;
  @Autowired private ConcertRepository concertRepository;
  @Autowired private ConcertCommentRepository concertCommentRepository;
  @Autowired private ReservationService reservationService;
  @PersistenceContext private EntityManager em;

  @Test
  public void 사용자는_회원가입이_가능해야_한다() {
    //given
    CreateUserRequest request = UserSamples.createUserRequest();

    //when
    String savedEmail = userService.create(request).getEmail();

    //then
    assertEquals(request.getEmail(), savedEmail);
  }

  @Test
  public void 회원은_포인트를_충정할_수_있어야_한다() {
    //given
    String email = createUser();
    UUID concertId = createConcert();

    User user = userRepository.findByEmail(email).get();
    Concert concert = concertRepository.findById(concertId).get();

    //when & then
    assertEquals(0L, user.currentPoints());

    final long CHARGE_POINT = 3000L;
    ChargePointRequest request = new ChargePointRequest(CHARGE_POINT);
    userService.chargePoint(request, user.getId());

    assertEquals(CHARGE_POINT, user.currentPoints());
  }

  @Test
  public void 회원은_콘서트에_예약할_수_있어야_한다() {
    //given
    String email = createUser();
    UUID concertId = createConcert();

    User user = userRepository.findByEmail(email).get();
    Concert concert = concertRepository.findById(concertId).get();

    //when
    userService.chargePoint(new ChargePointRequest(100000L), user.getId());
    reservationService.concertReservation(new ReserveConcertRequest(concert.getId(), "A1"), user.getId());

    //then
    assertEquals(1, user.getReservationList().size());
  }

  @Test
  public void 회원은_콘서트에_댓글을_달_수_있어야_한다() {
    //given
    String email = createUser();
    UUID concertId = createConcert();

    User user = userRepository.findByEmail(email).get();
    Concert concert = concertRepository.findById(concertId).get();

    //when
    concertService.createComment(new CreateConcertCommentRequest(concert.getId(), "댓글작성"), user.getId());

    //then
    assertEquals(1, concert.getCommentList().size());
  }

  @Test
  public void 회원은_작성한_댓글을_삭제할_수_있어야_한다() {
    //given
    String email = createUser();
    UUID concertId = createConcert();

    User user = userRepository.findByEmail(email).get();
    Concert concert = concertRepository.findById(concertId).get();

    ConcertComment comment = concert.createComment("테스트댓글");
    concertCommentRepository.save(comment);
    comment.setCreatedBy(user.getId().toString());
    concertCommentRepository.save(comment);

    //when
    concertService.deleteComment(new DeleteConcertCommentRequest(comment.getId()), user.getId());

    //then
    Optional<ConcertComment> foundComment = concertCommentRepository.findById(comment.getId());
    assertTrue(foundComment.isEmpty());
  }

  private String createUser() {
    CreateUserRequest createUserRequest = UserSamples.createUserRequest();
    return userService.create(createUserRequest).getEmail();
  }

  private UUID createConcert() {
    CreateConcertRequest concertRequest = ConcertSamples.createConcertRequest();
    UUID concertId = concertService.create(concertRequest, UUID.randomUUID()).getId();
    Concert concert = concertRepository.findById(concertId).get();
    concertService.saveSeatList(concertRequest, concert)
      .forEach(concert.getSeatList()::add);

    em.clear();
    concert = concertRepository.findById(concertId).get();
    return concert.getId();
  }
}
