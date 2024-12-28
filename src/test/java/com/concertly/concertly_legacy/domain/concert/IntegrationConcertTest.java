package com.concertly.concertly_legacy.domain.concert;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.concert.service.ConcertService;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.domain.user.service.UserService;
import com.concertly.concertly_legacy.smaple.ConcertSamples;
import com.concertly.concertly_legacy.smaple.UserSamples;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import com.concertly.concertly_legacy.web.concert.dto.FetchReservableConcertSeatsRequest;
import com.concertly.concertly_legacy.web.concert.dto.FetchReservableConcertSeatsResponse;
import com.concertly.concertly_legacy.web.user.dto.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class IntegrationConcertTest {

  @Autowired private UserService userService;
  @Autowired private UserRepository userRepository;
  @Autowired private ConcertService concertService;
  @Autowired private ConcertRepository concertRepository;

  @Test
  public void 콘서트_정보에는_공연명_공연장소_공연시간_좌석정보가_포함되어야_한다() throws Exception{
    //given
    Concert concert = createConcert();

    //when
    List<FetchReservableConcertSeatsResponse> responses = concertService.fetchReservableConcerts();

    //then
    assertEquals(1, responses.size());
    FetchReservableConcertSeatsResponse response = responses.get(0);
    assertEquals(concert.getTitle(), response.getConcertName());
    assertEquals(concert.getStartTime(), response.getConcertStartTime());
    assertEquals(concert.getEndTime(), response.getConcertEndTime());
    assertFalse(response.getReservableSeats().isEmpty());
  }

  @Test
  public void 콘서트별_예약_가능한_좌석_현황이_표시되어야_한다() {
    //given
    Concert concert = createConcert();

    //when
    FetchReservableConcertSeatsRequest request = new FetchReservableConcertSeatsRequest(concert.getId());
    FetchReservableConcertSeatsResponse response = concertService.fetchReservableSeats(request);

    //then
    assertEquals(concert.getTitle(), response.getConcertName());
    assertEquals(concert.getStartTime(), response.getConcertStartTime());
    assertEquals(concert.getEndTime(), response.getConcertEndTime());
    assertFalse(response.getReservableSeats().isEmpty());
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
