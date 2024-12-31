package com.concertly.concertly_legacy.domain.concert.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@Data
public class BaseConcertDto extends BaseDto {
  public String title;
  public String location;
  public LocalDateTime startTime;
  public LocalDateTime endTime;

  public List<BaseSeatDto> seatList;
  public List<BaseConcertCommentDto> commentList;

  public static BaseConcertDto from(Concert concert) {
    BaseConcertDto baseDto = BaseConcertDto.fromBase(concert, BaseConcertDto.class);
    baseDto.setTitle(concert.getTitle());
    baseDto.setLocation(concert.getLocation());
    baseDto.setStartTime(concert.getStartTime());
    baseDto.setEndTime(concert.getEndTime());
    return baseDto;
  }

}
