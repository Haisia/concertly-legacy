package com.concertly.concertly_legacy.domain.concert.dto;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseConcertDtoBuilder {
  private final Concert concert;
  private BaseConcertDto target;

  public BaseConcertDtoBuilder withSeats() {
    target.setSeatList(BaseSeatDto.from(concert.findAvailableSeatList()));
    return this;
  }

  public BaseConcertDtoBuilder withComments() {
    target.setCommentList(BaseConcertCommentDto.from(concert.getCommentList()));
    return this;
  }

  public static BaseConcertDtoBuilder builder(Concert concert) {
    BaseConcertDtoBuilder builder = new BaseConcertDtoBuilder(concert);
    builder.target = BaseConcertDto.from(concert);
    return builder;
  }

  public BaseConcertDto build() {
    return target;
  }
}
