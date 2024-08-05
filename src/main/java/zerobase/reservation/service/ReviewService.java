package zerobase.reservation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.reservation.exception.NoReviewException;
import zerobase.reservation.model.Review;
import zerobase.reservation.persist.ReservationRepository;
import zerobase.reservation.persist.ReviewRepository;
import zerobase.reservation.persist.entity.ReviewEntity;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository rservationRepository;

    /* **************************************************************************************
     * getStoreReview : 해당 상점의 리뷰 조회
     * @param userId
     * ************************************************************************************** */
    public Page<ReviewEntity> getStoreReview(Long store_id, Pageable pageable) {
        return this.reviewRepository.findByStoreId(store_id, pageable);
    }

    /* **************************************************************************************
     * getStoreReview : 해당 상점의 리뷰 조회
     * @param userId
     * ************************************************************************************** */
    public Page<ReviewEntity> getUserReview(Long user_id, Pageable pageable) {
        return this.reviewRepository.findByUserId(user_id, pageable);
    }

    /* **************************************************************************************
     * insertReview : 리뷰 추가
     * @param reservation_id, user_Id
     * ************************************************************************************** */
    public Review insertReview(Long reservation_id, Long store_id, Long user_id, String content) {
        boolean exists = this.rservationRepository.existsByIdAndVisitYn(reservation_id, "Y");
        if (!exists) {
            throw new RuntimeException("등록 가능한 예약이 존재하지 않습니다.");
        }

        Review newReview = new Review();
        newReview.setReservation_id(reservation_id);
        newReview.setStore_id(store_id);
        newReview.setUser_id(user_id);
        newReview.setContent(content);
        this.reviewRepository.save(new ReviewEntity(newReview));

        return newReview;
    }

    /* **************************************************************************************
     * deleteReview : 리뷰 삭제
     * @param id
     * ************************************************************************************** */
    public Long deleteReview(Long id, Long user_id) {
        boolean exists = this.reviewRepository.existsByIdAndUserId(id, user_id);
        if (!exists) {
            throw new NoReviewException();
        }

        var review = this.reviewRepository.findById(id)
                                            .orElseThrow(() -> new NoReviewException());
        this.reviewRepository.delete(review);

        return review.getId();
    }

    /* **************************************************************************************
     * updateReview : 리뷰 수정
     * @param updateReview
     * ************************************************************************************** */
    public Long updateReview(Review updateReview, Long user_id) {
        var review = this.reviewRepository.findByIdAndUserId(updateReview.getReview_id(), user_id)
                                            .orElseThrow(() -> new NoReviewException());

        review.setContent(updateReview.getContent());
        this.reviewRepository.save(review);

        return review.getId();
    }
}
