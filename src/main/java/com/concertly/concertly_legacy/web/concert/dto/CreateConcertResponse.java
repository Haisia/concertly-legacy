package com.concertly.concertly_legacy.web.concert.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.commons.enums.SeatStatus;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseSeatDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateConcertResponse extends BaseDto {
  public UUID concertId;
  public String title;
  public String location;
  public LocalDateTime startTime;
  public LocalDateTime endTime;

  public List<SeatResponse> seats;

  public static CreateConcertResponse from(BaseConcertDto dto) {
    CreateConcertResponse response = CreateConcertResponse.fromBase(dto, CreateConcertResponse.class);
    response.setConcertId(dto.getId());
    response.setTitle(dto.getTitle());
    response.setLocation(dto.getLocation());
    response.setStartTime(dto.getStartTime());
    response.setEndTime(dto.getEndTime());
    response.setSeats(SeatResponse.from(dto.getSeatList()));
    return response;
  }

  @EqualsAndHashCode(callSuper = true)
  @Data
  public static class SeatResponse extends BaseDto {
    public String seatNumber;
    public SeatStatus status;
    public Long price;

    public static SeatResponse from(BaseSeatDto dto) {
      SeatResponse response = SeatResponse.fromBase(dto, SeatResponse.class);
      response.setSeatNumber(dto.getSeatNumber());
      response.setStatus(dto.getStatus());
      response.setPrice(dto.getPrice());
      return response;
    }

    public static List<SeatResponse> from(List<BaseSeatDto> dtoList) {
      return dtoList.stream()
        .map(SeatResponse::from)
        .toList()
        ;
    }
  }
}
