package com.concertly.concertly_legacy.web.concert.dto;

import com.concertly.concertly_legacy.commons.dto.BaseDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseConcertDto;
import com.concertly.concertly_legacy.domain.concert.dto.BaseSeatDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@Schema(description = "예약 가능한 콘서트 좌석 응답 Dto")
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
@JsonPropertyOrder({"concertId", "concertTitle", "concertStartTime", "concertEndTime", "reservableSeatResponses"})
public class FetchReservableConcertSeatsResponse extends BaseDto {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  public UUID concertId;

  @Schema(description = CONCERT_TITLE_SCHEMA_DESCRIPTION, example = CONCERT_TITLE_SCHEMA_EXAMPLE)
  public String concertTitle;

  @Schema(description = CONCERT_START_TIME_SCHEMA_DESCRIPTION, example = CONCERT_START_TIME_SCHEMA_EXAMPLE)
  public LocalDateTime concertStartTime;

  @Schema(description = CONCERT_END_TIME_SCHEMA_DESCRIPTION, example = CONCERT_END_TIME_SCHEMA_EXAMPLE)
  public LocalDateTime concertEndTime;

  @Schema(description = "예약가능한 좌석 정보")
  public List<ReservableSeatResponse> reservableSeatResponses;

  public static FetchReservableConcertSeatsResponse from(BaseConcertDto dto) {
    FetchReservableConcertSeatsResponse response = FetchReservableConcertSeatsResponse.fromBase(dto, FetchReservableConcertSeatsResponse.class);
    response.setConcertId(dto.getId());
    response.setConcertTitle(dto.getTitle());
    response.setConcertStartTime(dto.getStartTime());
    response.setConcertEndTime(dto.getEndTime());
    response.setReservableSeatResponses(ReservableSeatResponse.from(dto.getSeatList()));
    return response;
  }

  public static List<FetchReservableConcertSeatsResponse> from(List<BaseConcertDto> seatList) {
    return seatList.stream()
      .map(FetchReservableConcertSeatsResponse::from)
      .toList()
      ;
  }

  @EqualsAndHashCode(callSuper = true)
  @Data
  public static class ReservableSeatResponse extends BaseDto {

    @Schema(description = CONCERT_SEAT_NUMBER_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_NUMBER_SCHEMA_EXAMPLE)
    public String seatNumber;

    @Schema(description = CONCERT_SEAT_PRICE_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_PRICE_SCHEMA_EXAMPLE)
    public Long price;

    public static ReservableSeatResponse from(BaseSeatDto dto) {
      ReservableSeatResponse response = ReservableSeatResponse.fromBase(dto, ReservableSeatResponse.class);
      response.setSeatNumber(dto.getSeatNumber());
      response.setPrice(dto.getPrice());
      return response;
    }

    public static List<ReservableSeatResponse> from(List<BaseSeatDto> dtoList) {
      return dtoList.stream()
        .map(ReservableSeatResponse::from)
        .toList()
        ;
    }
  }
}
