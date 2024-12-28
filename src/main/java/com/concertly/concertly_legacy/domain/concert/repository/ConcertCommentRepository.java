package com.concertly.concertly_legacy.domain.concert.repository;

import com.concertly.concertly_legacy.domain.concert.entity.ConcertComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConcertCommentRepository extends JpaRepository<ConcertComment, UUID> {
}
