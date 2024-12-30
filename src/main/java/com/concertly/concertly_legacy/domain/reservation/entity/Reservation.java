package com.concertly.concertly_legacy.domain.reservation.entity;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import com.concertly.concertly_legacy.commons.exceptions.UnableStatusException;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = 'N'")
@Table(name = "reservation")
@Entity
public class Reservation extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "seat_id", nullable = false)
  private Seat seat;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public static Reservation reserve(Seat seat, User user) {
    if (seat.getConcert().getStartTime().isBefore(LocalDateTime.now().plusHours(24))) {
      throw new UnableStatusException("예약은 공연 시작 24시간 이전까지만 가능합니다.");
    }
    seat.reservation();
    user.spendPoints(seat.getPrice());
    Reservation reservation = new Reservation(seat, user);
    user.getReservationList().add(reservation);
    return reservation;
  }

  public void cancel() {
    if (!this.deleted.equals("N")) {
      throw new UnableStatusException("이미 취소된 예약입니다.");
    }

    if (isLessThan24HoursToStart()) {
      throw new UnableStatusException("예약 취소는 공연 시작 24시간 전까지만 가능합니다.");
    }

    Long price = seat.cancel().getPrice();
    user.chargePoints(price);
    this.deleted = "Y";
  }

  public boolean isOwner(UUID userId) {
    return user.getId().equals(userId);
  }

  public boolean isLessThan24HoursToStart() {
    return LocalDateTime.now()
      .plusHours(24)
      .isAfter(this.seat.getConcert().getStartTime());
  }

  private Reservation(Seat seat, User user) {
    this.seat = seat;
    this.user = user;
  }
}
