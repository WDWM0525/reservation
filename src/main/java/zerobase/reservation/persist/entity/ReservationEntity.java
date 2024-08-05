package zerobase.reservation.persist.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.reservation.model.Reservation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "RESERVATION")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;         // 예약 id

    @Column(name = "USER_ID")
    private Long userId;    // 사용자 id

    @Column(name = "STORE_ID")
    private Long storeId;   // 상점 id

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;  // 예약일시

    @Column(name = "VISIT_YN")
    private String visitYn; // 방문여부

    public ReservationEntity(Reservation reservation) {
        this.userId  = reservation.getUser_id();
        this.storeId = reservation.getStore_id();
        this.date = reservation.getDate();
        this.visitYn = reservation.getVisit_yn();
    }

}
