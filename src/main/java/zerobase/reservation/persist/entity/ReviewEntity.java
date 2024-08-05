package zerobase.reservation.persist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zerobase.reservation.model.Review;

import javax.persistence.*;

@Entity(name = "REVIEW")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;                // 리뷰 id

    @Column(name = "RESERVATION_ID")
    private Long reservationId;    // 예약 id

    @Column(name = "STORE_ID")
    private Long storeId;           // 상점 id

    @Column(name = "USER_ID")
    private Long userId;           // 사용자 id

    private String content;          // 리뷰 내용

    public ReviewEntity(Review review) {
        this.reservationId = review.getReservation_id();
        this.storeId = review.getStore_id();
        this.userId = review.getUser_id();
        this.content = review.getContent();
    }

}
