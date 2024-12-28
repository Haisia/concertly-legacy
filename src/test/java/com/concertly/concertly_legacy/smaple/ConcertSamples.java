package com.concertly.concertly_legacy.smaple;

import com.concertly.concertly_legacy.web.concert.dto.CreateConcertRequest;

import java.time.LocalDateTime;
import java.util.List;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

public class ConcertSamples {

  public static CreateConcertRequest createConcertRequest() {
    CreateConcertRequest request = new CreateConcertRequest();
    request.setTitle(CONCERT_TITLE_SCHEMA_EXAMPLE);
    request.setLocation(CONCERT_LOCATION_SCHEMA_EXAMPLE);
    request.setStartTime(LocalDateTime.parse(CONCERT_START_TIME_SCHEMA_EXAMPLE));
    request.setEndTime(LocalDateTime.parse(CONCERT_END_TIME_SCHEMA_EXAMPLE));

    CreateConcertRequest.Seats seats1 = new CreateConcertRequest.Seats("A", 10L, 1000L);
    CreateConcertRequest.Seats seats2 = new CreateConcertRequest.Seats("B", 20L, 2000L);
    CreateConcertRequest.Seats seats3 = new CreateConcertRequest.Seats("C", 30L, 3000L);

    request.setSeatList(List.of(seats1, seats2, seats3));

    return request;
  }

}
