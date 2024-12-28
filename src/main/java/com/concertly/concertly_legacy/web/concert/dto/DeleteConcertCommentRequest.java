package com.concertly.concertly_legacy.web.concert.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteConcertCommentRequest {
  public UUID commentId;
}
