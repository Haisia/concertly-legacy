package com.concertly.concertly_legacy.smaple;

import com.concertly.concertly_legacy.web.concert.dto.CreateConcertCommentRequest;
import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;
import com.concertly.concertly_legacy.web.concert.dto.FetchReservableConcertSeatsRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

public class ConcertSamples {

  public static CreateConcertRequest createConcertRequest() {
    CreateConcertRequest request = new CreateConcertRequest();
    request.setTitle(CONCERT_TITLE_SCHEMA_EXAMPLE);
    request.setLocation(CONCERT_LOCATION_SCHEMA_EXAMPLE);
    request.setStartTime(LocalDateTime.now().plusYears(10));
    request.setEndTime(request.getStartTime().plusHours(2));

    CreateConcertRequest.Seats seats1 = new CreateConcertRequest.Seats("A", 10L, 1000L);
    CreateConcertRequest.Seats seats2 = new CreateConcertRequest.Seats("B", 20L, 2000L);
    CreateConcertRequest.Seats seats3 = new CreateConcertRequest.Seats("C", 30L, 3000L);

    request.setSeatList(List.of(seats1, seats2, seats3));

    return request;
  }

  public static CreateConcertCommentRequest createConcertCommentRequest() {
    return new CreateConcertCommentRequest(UUID.randomUUID(), "샘플댓글");
  }

  public static FetchReservableConcertSeatsRequest fetchReservableConcertSeatsRequest() {
    return new FetchReservableConcertSeatsRequest(UUID.randomUUID());
  }

}
