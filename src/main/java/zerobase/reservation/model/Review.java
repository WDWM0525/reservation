package zerobase.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long review_id;
    private Long reservation_id;
    private Long store_id;
    private Long user_id;
    private String content;
}
