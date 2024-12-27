package com.concertly.concertly_legacy.domain.user.entity;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.user.entity.vo.Point;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = 'N'")
@Table(name = "users")
@Entity
public class User extends BaseEntity {
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Embedded
  @Column(name = "point", nullable = false)
  private Point point;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Reservation> reservationList = new ArrayList<>();

  private User(String email, String password) {
    this.email = email;
    this.password = password;
    this.point = new Point();
  }

  public User updatePassword(String password) {
    this.password = password;
    return this;
  }

  public User chargePoints(Long point) {
    this.point = this.point.add(point);
    return this;
  }

  public User spendPoints(Long point) {
    this.point = this.point.subtract(point);
    return this;
  }

  public Long currentPoints() {
    return this.point.getPoint();
  }

  public static User of(String email, String password) {
    return new User(email, password);
  }
}
