package zerobase.reservation.web;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.model.Review;
import zerobase.reservation.persist.entity.MemberEntity;
import zerobase.reservation.persist.entity.ReviewEntity;
import zerobase.reservation.service.ReviewService;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;

    /* **************************************************************************************
     * GET /review/searchStoreReview
     * searchStoreReview : 해당 상점의 리뷰 조회
     * @param pageable, member
     * ************************************************************************************** */
    @GetMapping("/searchStoreReview/{store_id}")
    public ResponseEntity<?> getStoreReview(final Pageable pageable, @PathVariable Long store_id, @AuthenticationPrincipal MemberEntity member) {
        if (ObjectUtils.isEmpty(store_id)) {
            throw new RuntimeException("store_id is empty");
        }

        Page<ReviewEntity> reviewList = this.reviewService.getStoreReview(store_id, pageable);
        return ResponseEntity.ok(reviewList);
    }

    /* **************************************************************************************
     * GET /review/searchUserReview
     * searchUserReview : 해당 유저의 리뷰 조회
     * @param pageable, member
     * ************************************************************************************** */
    @GetMapping("/searchUserReview")
    public ResponseEntity<?> searchUserReview(final Pageable pageable, @AuthenticationPrincipal MemberEntity member) {
        Long user_Id = member.getId();
        Page<ReviewEntity> reviewList = this.reviewService.getUserReview(user_Id, pageable);
        return ResponseEntity.ok(reviewList);
    }

    /* **************************************************************************************
     * Post /review
     * insertReview : 리뷰 추가
     * @param requset, member
     * ************************************************************************************** */
    @PostMapping
    public ResponseEntity<?> insertReview(@RequestBody Review request
                                                , @AuthenticationPrincipal MemberEntity member) {
        Long reservationId = request.getReservation_id();
        if (ObjectUtils.isEmpty(reservationId)) {
            throw new RuntimeException("reservationId is empty");
        }

        Long storeId = request.getStore_id();
        if (ObjectUtils.isEmpty(storeId)) {
            throw new RuntimeException("storeId is empty");
        }

        String content = request.getContent();
        if (ObjectUtils.isEmpty(content)) {
            throw new RuntimeException("content is empty");
        }

        Long userId = member.getId();

        Review review = this.reviewService.insertReview(reservationId, storeId, userId, content);
        return ResponseEntity.ok(review);
    }

    /* **************************************************************************************
     * Delete /review/{review_id}
     * deleteReview : 리뷰 삭제
     * @param requset, member
     * ************************************************************************************** */
    @DeleteMapping("/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long review_id
                                        , @AuthenticationPrincipal MemberEntity member) {
        if (ObjectUtils.isEmpty(review_id)) {
            throw new RuntimeException("review_id is empty");
        }

        Long user_Id = member.getId();

        Long id = this.reviewService.deleteReview(review_id, user_Id);

        return ResponseEntity.ok(id + " 리뷰 삭제 성공");
    }

    /* **************************************************************************************
     * Put /review/updateReview/{review_id}
     * updateReview : 리뷰 수정
     * @param reservation_id, member
     * ************************************************************************************** */
    @PutMapping("/updateReview/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable Long review_id
                                                    , @RequestBody Review request
                                                    , @AuthenticationPrincipal MemberEntity member) {

        if (ObjectUtils.isEmpty(review_id)) {
            throw new RuntimeException("review_id is empty");
        }
        request.setReview_id(review_id);

        Long user_id = member.getId();

        Long id = this.reviewService.updateReview(request, user_id);
        return ResponseEntity.ok(id + " 리뷰 수정 성공");
    }
}
