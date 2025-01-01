package com.concertly.concertly_legacy.web.concert.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertCommentDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonPropertyOrder({"concertId", "comment"})
public class CreateConcertCommentResponse extends BaseDto {
  private UUID concertId;
  private String comment;

  public static CreateConcertCommentResponse from(BaseConcertCommentDto dto) {
    CreateConcertCommentResponse response = CreateConcertCommentResponse.fromBase(dto, CreateConcertCommentResponse.class);
    response.setConcertId(dto.getConcertId());
    response.setComment(dto.getComment());
    return response;
  }
}
