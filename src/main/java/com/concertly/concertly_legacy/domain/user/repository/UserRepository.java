package com.concertly.concertly_legacy.domain.user.repository;

import com.concertly.concertly_legacy.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
