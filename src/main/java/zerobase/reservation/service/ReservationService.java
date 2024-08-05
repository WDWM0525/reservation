package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.reservation.exception.NoReservationException;
import zerobase.reservation.model.Reservation;
import zerobase.reservation.persist.ReservationRepository;
import zerobase.reservation.persist.entity.ReservationEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    /* **************************************************************************************
     * getStoreReservation : 해당 상점의 예약 조회
     * @param storeId
     * ************************************************************************************** */
    public ReservationEntity getStoreReservation(Long store_id) {
        return this.reservationRepository.findByStoreId(store_id);
    }

    /* **************************************************************************************
     * getUserReservation : 해당 사용자의 예약 조회
     * @param userId
     * ************************************************************************************** */
    public Page<ReservationEntity> getUserReservation(Long user_id, Pageable pageable) {
        return this.reservationRepository.findByUserId(user_id, pageable);
    }

    /* **************************************************************************************
     * insertReservation : 예약 추가
     * @param storeId, user_Id, date
     * ************************************************************************************** */
    public Reservation insertReservation(Long store_id, Long user_id, LocalDateTime date) {
        boolean exists = this.reservationRepository.existsByStoreIdAndDate(store_id, date);
        if (exists) {
            throw new RuntimeException("예약이 존재하여 예약 불가합니다.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser_id(user_id);
        reservation.setStore_id(store_id);
        reservation.setDate(date);
        reservation.setVisit_yn("N");
        this.reservationRepository.save(new ReservationEntity(reservation));

        return reservation;
    }

    /* **************************************************************************************
     * deleteReservation : 예약 삭제(= 취소)
     * @param id, user_id
     * ************************************************************************************** */
    public Long deleteReservation(Long id, Long user_id) {
        boolean exists = this.reservationRepository.existsByIdAndUserId(id, user_id);
        if (!exists) {
            throw new NoReservationException();
        }

        var reservation = this.reservationRepository.findById(id)
                                                    .orElseThrow(() -> new NoReservationException());
        this.reservationRepository.delete(reservation);

        return reservation.getId();
    }

    /* **************************************************************************************
     * updateVisitYn : 예약 방문여부 변경
     * @param id, user_id
     * ************************************************************************************** */
    public Long updateVisitYn(Long id, Long user_id) {
        boolean exists = this.reservationRepository.existsByIdAndUserId(id, user_id);
        if (!exists) {
            throw new NoReservationException();
        }

        var reservation = this.reservationRepository.findById(id)
                                                    .orElseThrow(() -> new NoReservationException());


        // 예약시간 10분 전인지 확인
        LocalDateTime visitTime = LocalDateTime.now();
        LocalDateTime reservationTime = reservation.getDate();

        //두 시간 차이를 분으로 환산
        LocalTime start = reservationTime.toLocalTime();
        LocalTime end   = visitTime.toLocalTime();
        Duration diff = Duration.between(start, end);
        long diffMin = diff.toMinutes();
        if (Math.abs(diffMin) < 10) {
            throw new RuntimeException("예약시간 10분 전까지만 인정됩니다.");
        }

        reservation.setVisitYn("Y");
        this.reservationRepository.save(reservation);

        return reservation.getId();
    }
}
