package api.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RequestInfo {

    private String method;
    private Map<String, String> headers;
    private Map<String, String> parameters;
}
