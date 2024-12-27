package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.commons.exceptions.UnableStatusException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.reservation.repository.ReservationRepository;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.web.reservation.dto.ConcertReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

  private final ConcertRepository concertRepository;
  private final ReservationRepository reservationRepository;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public void concertReservation(ConcertReservationRequest request, Long requesterId) {
    Concert concert = concertRepository.findWithSeatsById(request.getConcertId())
      .orElseThrow(() -> new NotFoundException("Concert", "concertId", request.getConcertId().toString()));

    Seat foundSeat = concert.findSeatBySeatNumber(request.getSeatNumber());
    if (!foundSeat.isAvailable()) {
      throw new UnableStatusException("예약할 수 없는 좌석입니다.");
    }

    User user = userRepository.findById(requesterId)
      .orElseThrow(() -> new NotFoundException("User", "id", requesterId.toString()));

    Reservation reservation = Reservation.reserve(foundSeat, user);
    reservationRepository.save(reservation);
  }


}
