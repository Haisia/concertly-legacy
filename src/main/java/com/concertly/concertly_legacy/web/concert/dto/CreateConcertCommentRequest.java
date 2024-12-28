package com.concertly.concertly_legacy.web.concert.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateConcertCommentRequest {
  public UUID concertId;
  public String comment;
}
