package com.concertly.concertly_legacy.commons.dto;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import com.concertly.concertly_legacy.commons.interfaces.Metadata;
import com.concertly.concertly_legacy.domain.concert.dto.BaseSeatDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseDto implements Metadata {
  public UUID id;
  public String deleted;
  public String createdBy;
  public LocalDateTime createdAt;
  public String updatedBy;
  public LocalDateTime updatedAt;

  public static BaseDto fromBase(Metadata entity) {
    BaseSeatDto dto = new BaseSeatDto();
    dto.id = entity.getId();
    dto.deleted = entity.getDeleted();
    dto.createdBy = entity.getCreatedBy();
    dto.createdAt = entity.getCreatedAt();
    dto.updatedBy = entity.getUpdatedBy();
    dto.updatedAt = entity.getUpdatedAt();
    return dto;
  }

}
