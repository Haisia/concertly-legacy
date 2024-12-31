package com.concertly.concertly_legacy.web.concert.dto;

import com.concertly.concertly_legacy.commons.annotations.ValidUUID;
import com.concertly.concertly_legacy.commons.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.ID_PATTERN_MESSAGE;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "콘서트 댓글작성 요청 Dto")
@AllArgsConstructor
@Data
public class CreateConcertCommentRequest extends BaseDto {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  @ValidUUID(message = ID_PATTERN_MESSAGE)
  public UUID concertId;

  @Schema(description = CONCERT_COMMENT_SCHEMA_DESCRIPTION, example = CONCERT_COMMENT_SCHEMA_EXAMPLE)
  @Pattern(regexp = CONCERT_COMMENT_PATTERN_REGEX, message = CONCERT_COMMENT_PATTERN_MESSAGE)
  public String comment;
}
