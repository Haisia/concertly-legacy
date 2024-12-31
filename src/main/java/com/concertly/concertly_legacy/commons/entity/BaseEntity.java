package com.concertly.concertly_legacy.commons.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  // 'Y' or 'N'
  @Column
  protected String deleted = "N";

  @CreatedDate
  @Column(updatable = false)
  protected LocalDateTime createdAt;

  @CreatedBy
  @Column(updatable = false)
  protected String createdBy;

  @LastModifiedDate
  @Column(insertable = false)
  protected LocalDateTime updatedAt;

  @LastModifiedBy
  @Column(insertable = false)
  protected String updatedBy;

}