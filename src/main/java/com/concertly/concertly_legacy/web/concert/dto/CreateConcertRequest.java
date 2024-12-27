package com.concertly.concertly_legacy.web.concert.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateConcertRequest {
  public String title;
  public String location;
  public LocalDateTime startTime;
  public LocalDateTime endTime;
  public List<Seats> seatList;

  @Data
  public static class Seats {
    public String seatLabel;
    public Long seatMaxLineNumber;
    public Long price;
  }
}
