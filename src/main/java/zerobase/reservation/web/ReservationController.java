package zerobase.reservation.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.Reservation;
import zerobase.reservation.persist.entity.MemberEntity;
import zerobase.reservation.persist.entity.ReservationEntity;
import zerobase.reservation.service.ReservationService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;

    /* **************************************************************************************
     * Post /reservation
     * insertReservation : 예약 추가
     * @param requset, member
     * ************************************************************************************** */
    @PostMapping
    public ResponseEntity<?> insertReservation(@RequestBody Reservation request
                                            , @AuthenticationPrincipal MemberEntity member) {
        Long storeId = request.getStore_id();
        if (ObjectUtils.isEmpty(storeId)) {
            throw new RuntimeException("store is empty");
        }

        LocalDateTime date = request.getDate();
        if (ObjectUtils.isEmpty(date)) {
            throw new RuntimeException("date is empty");
        }

        Long user_Id = member.getId();

        Reservation reservation = this.reservationService.insertReservation(storeId, user_Id, date);
        return ResponseEntity.ok(reservation);
    }

    /* **************************************************************************************
     * Delete /reservation/{reservation_id}
     * deleteReservation : 예약 삭제(= 취소)
     * @param requset, member
     * ************************************************************************************** */
    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long reservation_id
                                            , @AuthenticationPrincipal MemberEntity member) {
        if (ObjectUtils.isEmpty(reservation_id)) {
            throw new RuntimeException("reservation_id is empty");
        }

        Long user_Id = member.getId();

        Long id = this.reservationService.deleteReservation(reservation_id, user_Id);
        
        return ResponseEntity.ok(id + " 예약 삭제 성공");
    }

    /* **************************************************************************************
     * GET /reservation/searchUserReservation
     * searchUserReservation : 해당 사용자의 예약 조회
     * @param pageable, member
     * ************************************************************************************** */
    @GetMapping("/searchUserReservation")
    public ResponseEntity<?> searchUserReservation(final Pageable pageable, @AuthenticationPrincipal MemberEntity member) {
        Long user_Id = member.getId();
        Page<ReservationEntity> reservationList = this.reservationService.getUserReservation(user_Id, pageable);
        return ResponseEntity.ok(reservationList);
    }

    /* **************************************************************************************
     * Put /reservation/updateVisitYn
     * updateVisitYn : 예약 방문여부 변경
     * @param reservation_id, member
     * ************************************************************************************** */
    @PutMapping("/updateVisitYn/{reservation_id}")
    public ResponseEntity<?> updateVisitYn(@PathVariable Long reservation_id
                                            , @AuthenticationPrincipal MemberEntity member) {
        if (ObjectUtils.isEmpty(reservation_id)) {
            throw new RuntimeException("reservation_id is empty");
        }

        Long user_Id = member.getId();
        System.out.println("reservation_id : " + reservation_id);
        System.out.println("user_Id : " + user_Id);

        Long id = this.reservationService.updateVisitYn(reservation_id, user_Id);
        return ResponseEntity.ok(id + " 방문 확인 성공");
    }
}
