package zerobase.reservation.exception;

import org.springframework.http.HttpStatus;

public class NoStoreException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 상점명 입니다.";
    }
}
