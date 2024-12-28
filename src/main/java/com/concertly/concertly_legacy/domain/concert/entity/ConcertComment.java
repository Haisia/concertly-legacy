package com.concertly.concertly_legacy.domain.concert.entity;

import com.concertly.concertly_legacy.commons.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE concert_comments SET deleted = 'Y' WHERE id = ?")
@Where(clause = "deleted = 'N'")
@Table(name = "concert_comments")
@Entity
public class ConcertComment extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "concert_id", nullable = false)
  private Concert concert;

  @Column(name = "comment", nullable = false)
  private String comment;

  protected ConcertComment(String comment, Concert concert) {
    this.comment = comment;
    this.concert = concert;
  }

}
