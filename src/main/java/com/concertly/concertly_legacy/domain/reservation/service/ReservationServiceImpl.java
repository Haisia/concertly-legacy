package com.concertly.concertly_legacy.domain.reservation.service;

import com.concertly.concertly_legacy.commons.exceptions.NotFoundException;
import com.concertly.concertly_legacy.commons.exceptions.PermissionDeniedException;
import com.concertly.concertly_legacy.commons.exceptions.UnableStatusException;
import com.concertly.concertly_legacy.domain.concert.entity.Concert;
import com.concertly.concertly_legacy.domain.concert.entity.Seat;
import com.concertly.concertly_legacy.domain.concert.repository.ConcertRepository;
import com.concertly.concertly_legacy.domain.reservation.dto.BaseReservationDto;
import com.concertly.concertly_legacy.domain.reservation.dto.BaseReservationDtoBuilder;
import com.concertly.concertly_legacy.domain.reservation.entity.Reservation;
import com.concertly.concertly_legacy.domain.reservation.repository.ReservationRepository;
import com.concertly.concertly_legacy.domain.user.entity.User;
import com.concertly.concertly_legacy.domain.user.repository.UserRepository;
import com.concertly.concertly_legacy.web.reservation.dto.CancelReservationRequest;
import com.concertly.concertly_legacy.web.reservation.dto.ReserveConcertRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

  private final ConcertRepository concertRepository;
  private final ReservationRepository reservationRepository;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public BaseReservationDto concertReservation(ReserveConcertRequest request, UUID requesterId) {
    Concert concert = concertRepository.findWithSeatsById(request.getConcertId())
      .orElseThrow(() -> new NotFoundException("Concert", "concertId", request.getConcertId().toString()));

    Seat foundSeat = concert.findSeatBySeatNumber(request.getSeatNumber());
    if (!foundSeat.isAvailable()) {
      throw new UnableStatusException("예약할 수 없는 좌석입니다.");
    }

    User user = userRepository.findById(requesterId)
      .orElseThrow(() -> new NotFoundException("User", "id", requesterId.toString()));

    Reservation reservation = Reservation.reserve(foundSeat, user);
    reservation = reservationRepository.save(reservation);

    return BaseReservationDtoBuilder.builder(reservation)
      .withConcert()
      .withSeat()
      .withUser()
      .build();
  }

  @Transactional
  @Override
  public void cancelReservation(CancelReservationRequest request, UUID requesterId) {
    Reservation reservation = reservationRepository.findById(request.getReservationId())
      .orElseThrow(() -> new NotFoundException("Reservation", "reservationId", request.getReservationId().toString()));

    if (!reservation.isOwner(requesterId)) {
      throw new PermissionDeniedException();
    }

    reservation.cancel();
    log.info("{} 님이 {} 예약을 취소했습니다.", requesterId, request.getReservationId());
  }

  @Transactional
  @Override
  public List<BaseReservationDto> fetchOwns(UUID requesterId) {
    User user = userRepository.findById(requesterId)
      .orElseThrow(() -> new NotFoundException("User", "id", requesterId.toString()));

    return user.ownReservations().stream()
      .map(reservation ->
        BaseReservationDtoBuilder.builder(reservation)
          .withConcert()
          .withSeat()
          .withUser()
          .build()
      ).toList();
  }
}
