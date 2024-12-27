package com.concertly.concertly_legacy.domain.user.entity.vo;

import com.concertly.concertly_legacy.commons.exceptions.InvalidParameterException;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Embeddable
public class Point {
  private final Long point;

  public Point() {
    this.point = 0L;
  }

  public Point(Long point) {
    this.point = point;
  }

  public Point add(Long point) {
    return new Point(this.point + point);
  }

  public Point subtract(Long point) {
    if (this.point < point) {
      throw new InvalidParameterException(String.format("보유 포인트 %d가 차감될 포인트 %d보다 적습니다.", point, this.point));
    }
    return new Point(this.point - point);
  }
}

