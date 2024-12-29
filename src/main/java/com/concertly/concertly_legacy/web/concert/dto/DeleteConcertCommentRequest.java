package com.concertly.concertly_legacy.web.concert.dto;

import com.concertly.concertly_legacy.commons.annotations.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.*;
import static com.concertly.concertly_legacy.commons.constants.DtoMetadataConstants.ID_PATTERN_MESSAGE;

@Schema(description = "콘서트 댓글 삭제 요청 Dto")
@AllArgsConstructor
@Data
public class DeleteConcertCommentRequest {

  @Schema(description = ID_SCHEMA_DESCRIPTION, example = ID_SCHEMA_EXAMPLE)
  @ValidUUID(message = ID_PATTERN_MESSAGE)
  public UUID commentId;
}
