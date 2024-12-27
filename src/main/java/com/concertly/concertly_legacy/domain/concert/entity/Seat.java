package com.concertly.concertly_legacy.domain.concert.entity;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import com.concertly.concertly_legacy.commons.enums.SeatStatus;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
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
  private SeatStatus status;

  @Column(name = "price", nullable = false)
  private Long price;

  @OneToOne(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
  private Reservation reservation;

  public boolean isAvailable() {
    return status == SeatStatus.AVAILABLE;
  }

  public Seat reservation() {
    if (status != SeatStatus.AVAILABLE) {
      throw new UnsupportedOperationException("해당 좌석은 예약할 수 없는 상태입니다.");
    }
    this.status = SeatStatus.RESERVED;
    return this;
  }

  public Seat cancel() {
    if (status != SeatStatus.RESERVED) {
      throw new UnsupportedOperationException("예약을 취소할 수 없는 상태입니다.");
    }
    status = SeatStatus.AVAILABLE;
    return this;
  }
}
