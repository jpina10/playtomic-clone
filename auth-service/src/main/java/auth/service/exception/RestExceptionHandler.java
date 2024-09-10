package auth.service.exception;

import auth.service.exception.model.RestErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(FeignException.NotFound.class)
    private ResponseEntity<RestErrorMessage> feignNotFoundHandler(FeignException.NotFound exception) throws JsonProcessingException {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        String detailMessage = exception.getMessage();

        // Extract the JSON part using substring
        int jsonStartIndex = detailMessage.indexOf("[{");
        int jsonEndIndex = detailMessage.lastIndexOf("}]") + 2;

        String jsonString = detailMessage.substring(jsonStartIndex, jsonEndIndex);

        // Parse the extracted JSON part
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);

        JsonNode messageNode = rootNode.get(0).get("message");

        String message = messageNode.asText();

        RestErrorMessage restErrorMessage = new RestErrorMessage(httpStatus.value(), httpStatus, message);

        return new ResponseEntity<>(restErrorMessage, restErrorMessage.error());
    }
}
