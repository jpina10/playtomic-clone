package user.service.exception.thirdparty;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class NameApiException extends RuntimeException {

    public NameApiException(String message){
        super(message);
    }
}
