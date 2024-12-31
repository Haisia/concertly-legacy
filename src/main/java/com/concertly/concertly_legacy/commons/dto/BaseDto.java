package com.concertly.concertly_legacy.commons.dto;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
public class BaseDto {
  public UUID id;
  public String deleted;
  public String createdBy;
  public LocalDateTime createdAt;
  public String updatedBy;
  public LocalDateTime updatedAt;

  public static <T extends BaseDto> T fromBase(BaseEntity entity, Class<T> clazz) {
    try {
      T dto = clazz.getDeclaredConstructor().newInstance();
      dto.setId(entity.getId());
      dto.setDeleted(entity.getDeleted());
      dto.setCreatedBy(entity.getCreatedBy());
      dto.setCreatedAt(entity.getCreatedAt());
      dto.setUpdatedBy(entity.getUpdatedBy());
      dto.setUpdatedAt(entity.getUpdatedAt());
      return dto;
    } catch (Exception e) {
      throw new RuntimeException("엔티티를 dto로 변환하는데 실패했습니다.", e);
    }
  }

  public static <T extends BaseDto> T fromBase(BaseDto dto, Class<T> clazz) {
    try {
      T result = clazz.getDeclaredConstructor().newInstance();
      result.setId(dto.getId());
      result.setDeleted(dto.getDeleted());
      result.setCreatedBy(dto.getCreatedBy());
      result.setCreatedAt(dto.getCreatedAt());
      result.setUpdatedBy(dto.getUpdatedBy());
      result.setUpdatedAt(dto.getUpdatedAt());
      return result;
    } catch (Exception e) {
      throw new RuntimeException("dto 를 변환하는데 실패했습니다.", e);
    }
  }
}
