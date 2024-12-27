package com.concertly.concertly_legacy.domain.concert.entity;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import com.concertly.concertly_legacy.commons.enums.ConcertStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = 'N'")
@Table(name = "seats")
@Entity
public class Seat extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "concert_id", nullable = false)
  private Concert concert;

  @Column(name = "seat_number", nullable = false)
  private String seatNumber;

  @Column(name = "status", nullable = false)
  private ConcertStatus status;
}
