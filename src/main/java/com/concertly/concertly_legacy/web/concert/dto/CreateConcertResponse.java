package com.concertly.concertly_legacy.web.concert.dto;

import com.concertly.concertly_legacy.commons.enums.SeatStatus;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CreateConcertResponse {
  public UUID concertId;
  public String title;
  public String location;
  public LocalDateTime startTime;
  public LocalDateTime endTime;

  public List<SeatResponse> seats;

  public static class SeatResponse {
    public String seatNumber;
    public SeatStatus status;
    public Long price;
  }

  public static CreateConcertResponse from(BaseConcertDto dto) {
    CreateConcertResponse response = new CreateConcertResponse();
    response.setConcertId(dto.getId());
    response.setTitle(dto.getTitle());
    response.setLocation(dto.getLocation());
    response.setStartTime(dto.getStartTime());
    response.setEndTime(dto.getEndTime());
    response.setStartTime(dto.getStartTime());
    response.setEndTime(dto.getEndTime());
  }

}
