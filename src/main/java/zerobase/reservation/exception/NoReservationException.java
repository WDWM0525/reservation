package zerobase.reservation.exception;

import org.springframework.http.HttpStatus;

public class NoReservationException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 예약 입니다.";
    }
}
