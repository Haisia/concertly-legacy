package com.concertly.concertly_legacy.domain.concert;

import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.concert.service.ConcertService;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.domain.user.service.UserService;
import com.concertly.concertly_legacy.smaple.ConcertSamples;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import com.concertly.concertly_legacy.web.concert.dto.FetchReservableConcertSeatsRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
  @PersistenceContext private EntityManager em;

  @Test
  public void 콘서트_정보에는_공연명_공연장소_공연시간_좌석정보가_포함되어야_한다() {
    //given
    Concert concert = createConcert();

    //when
    List<BaseConcertDto> results = concertService.fetchReservableConcerts();

    //then
    assertEquals(1, results.size());
    BaseConcertDto result = results.get(0);
    assertEquals(concert.getTitle(), result.getTitle());
    assertEquals(concert.getStartTime(), result.getStartTime());
    assertEquals(concert.getEndTime(), result.getEndTime());
    assertFalse(result.getSeatList().isEmpty());
  }

  @Test
  public void 콘서트별_예약_가능한_좌석_현황이_표시되어야_한다() {
    //given
    Concert concert = createConcert();

    //when
    FetchReservableConcertSeatsRequest request = new FetchReservableConcertSeatsRequest(concert.getId());
    BaseConcertDto concertDto = concertService.fetchReservableSeats(request);

    //then
    assertEquals(concert.getTitle(), concertDto.getTitle());
    assertEquals(concert.getStartTime(), concertDto.getStartTime());
    assertEquals(concert.getEndTime(), concertDto.getEndTime());
    assertFalse(concertDto.getSeatList().isEmpty());
  }

  private Concert createConcert() {
    CreateConcertRequest concertRequest = ConcertSamples.createConcertRequest();
    BaseConcertDto concertDto = concertService.create(concertRequest, UUID.randomUUID());
    Concert concert = concertRepository.findById(concertDto.getId()).get();
    concertService.saveSeatList(concertRequest, concert)
      .forEach(concert.getSeatList()::add);

    em.clear();
    concert = concertRepository.findById(concert.getId()).get();
    return concert;
  }
}
