package zerobase.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long reservation_id;
    private Long user_id;
    private Long store_id;
    private LocalDateTime date;
    private String visit_yn;
}
