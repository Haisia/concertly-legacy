package com.concertly.concertly_legacy.domain.concert.dto;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.ConcertComment;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseConcertDtoBuilder {
  private final Concert concert;
  private BaseConcertDto target;

  public BaseConcertDtoBuilder withSeats() {
    List<Seat> availableSeatList = concert.findAvailableSeatList();
    if (availableSeatList == null) throw new IllegalArgumentException("seatList 를 찾을 수 없습니다.");
    target.setSeatList(BaseSeatDto.from(availableSeatList));
    return this;
  }

  public BaseConcertDtoBuilder withComments() {
    List<ConcertComment> commentList = concert.getCommentList();
    if (commentList == null) throw new IllegalArgumentException("commentList 를 찾을 수 없습니다.");
    target.setCommentList(BaseConcertCommentDto.from(commentList));
    return this;
  }

  public static BaseConcertDtoBuilder builder(Concert concert) {
    if (concert == null) throw new IllegalArgumentException("concert 은 null 이면 안됩니다.");
    BaseConcertDtoBuilder builder = new BaseConcertDtoBuilder(concert);
    builder.target = BaseConcertDto.from(concert);
    return builder;
  }

  public BaseConcertDto build() {
    return target;
  }
}
