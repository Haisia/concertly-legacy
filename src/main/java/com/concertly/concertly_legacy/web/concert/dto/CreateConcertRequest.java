package com.concertly.concertly_legacy.web.concert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;

@Schema(description = "콘서트 생성 요청 Dto")
@Data
public class CreateConcertRequest {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = CONCERT_TITLE_SCHEMA_EXAMPLE)
  @Pattern(regexp = CONCERT_TITLE_PATTERN_REGEX, message = CONCERT_TITLE_PATTERN_MESSAGE)
  public String title;

  @Schema(description = CONCERT_LOCATION_SCHEMA_DESCRIPTION, example = CONCERT_LOCATION_SCHEMA_EXAMPLE)
  @Pattern(regexp = CONCERT_LOCATION_PATTERN_REGEX, message = CONCERT_LOCATION_PATTERN_MESSAGE)
  public String location;

  @Schema(description = CONCERT_START_TIME_SCHEMA_DESCRIPTION, example = CONCERT_START_TIME_SCHEMA_EXAMPLE)
  @Future(message = CONCERT_START_TIME_PATTERN_MESSAGE)
  public LocalDateTime startTime;

  @Schema(description = CONCERT_END_TIME_SCHEMA_DESCRIPTION, example = CONCERT_END_TIME_SCHEMA_EXAMPLE)
  @Future(message = CONCERT_END_TIME_PATTERN_MESSAGE)
  public LocalDateTime endTime;

  @Schema(description = "콘서트 좌석 리스트 정보.")
  public List<Seats> seatList;

  @AllArgsConstructor @NoArgsConstructor
  @Data
  public static class Seats {

    @Schema(description = SEAT_LABEL_SCHEMA_DESCRIPTION, example = SEAT_LABEL_SCHEMA_EXAMPLE)
    @Pattern(regexp = SEAT_LABEL_PATTERN_REGEX, message = SEAT_LABEL_PATTERN_MESSAGE)
    public String seatLabel;

    @Schema(description = SEAT_MAX_LINE_NUMBER_SCHEMA_DESCRIPTION, example = SEAT_MAX_LINE_NUMBER_SCHEMA_EXAMPLE)
    @Min(value = SEAT_MAX_LINE_NUMBER_MIN_VALUE, message = SEAT_MAX_LINE_NUMBER_MIN_MESSAGE)
    public Long seatMaxLineNumber;

    @Schema(description = CONCERT_SEAT_PRICE_SCHEMA_DESCRIPTION, example = CONCERT_SEAT_PRICE_SCHEMA_EXAMPLE)
    @Pattern(regexp = CONCERT_SEAT_PRICE_PATTERN_REGEX, message = CONCERT_SEAT_PRICE_PATTERN_MESSAGE)
    public Long price;
  }
}
