package com.concertly.concertly_legacy.domain.concert.repository;

import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, UUID> {

  @Query("SELECT c FROM Concert c LEFT JOIN FETCH c.seatList WHERE c.id = :concertId")
  Optional<Concert> findWithSeatsById(@Param("concertId") UUID concertId);

}
