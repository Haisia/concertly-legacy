package com.concertly.concertly_legacy.domain.concert.entity;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = 'N'")
@Table(name = "concerts")
@Entity
public class Concert extends BaseEntity {

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "location", nullable = false)
  private String location;

  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  @Column(name = "end_time", nullable = false)
  private LocalDateTime endTime;

  @OneToMany(mappedBy = "concert", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Seat> seatList = new ArrayList<>();

  public List<Seat> getAllSeatList() {
    return this.seatList;
  }

  public Seat findSeatBySeatNumber(String seatNumber) {
    return this.seatList.stream()
      .filter(seat -> seat.getSeatNumber().equals(seatNumber))
      .findFirst()
      .orElseThrow(() -> new NotFoundException("Seat", "seatNumber", seatNumber))
      ;
  }
}
