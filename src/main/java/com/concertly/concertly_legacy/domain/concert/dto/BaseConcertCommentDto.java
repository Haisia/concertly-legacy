package com.concertly.concertly_legacy.domain.concert.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.concert.entity.ConcertComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseConcertCommentDto extends BaseDto {
  public UUID concertId;
  public String comment;

  public static BaseConcertCommentDto from(ConcertComment comment) {
    return getBaseDto(comment);
  }

  public static List<BaseConcertCommentDto> from(List<ConcertComment> comments) {
    return comments.stream()
      .map(BaseConcertCommentDto::from)
      .toList()
      ;
  }

  private static BaseConcertCommentDto getBaseDto(ConcertComment comment) {
    BaseConcertCommentDto baseDto = BaseConcertCommentDto.fromBase(comment, BaseConcertCommentDto.class);
    baseDto.setConcertId(comment.getConcert().getId());
    baseDto.setComment(comment.getComment());
    return baseDto;
  }
}