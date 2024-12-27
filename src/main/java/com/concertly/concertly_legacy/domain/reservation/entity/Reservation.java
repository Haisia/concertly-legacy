package com.concertly.concertly_legacy.domain.reservation.entity;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

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
    seat.reservation();
    user.spendPoints(seat.getPrice());
    return new Reservation(seat, user);
  }

  public void cancel() {
    Long price = seat.cancel().getPrice();
    user.chargePoints(price);
  }

  private Reservation(Seat seat, User user) {
    this.seat = seat;
    this.user = user;
  }
}
