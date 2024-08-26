package api.gateway.filter;

import api.gateway.model.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    private static final String GATEWAY_REQUEST_URL = "org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRequestUrl";
    private static final String RECEIVED_CALL = "Received a {} request to the gateway for: {} with headers: {} and parameters: {}";
    private static final String FORWARDED_CALL = "Forwarded call to service: {} with uri: {}";
    private static final String NO_ROUTE = "No route found for the request.";

    public LoggingFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            RequestInfo requestInfo = getRequestInfo(exchange);

            log.info(RECEIVED_CALL, requestInfo.getMethod(), exchange.getRequest().getURI(), requestInfo.getHeaders(), requestInfo.getParameters());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                String forwardedUri = Objects.requireNonNull(exchange.getAttribute(GATEWAY_REQUEST_URL)).toString();
                Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

                Optional.ofNullable(route)
                        .ifPresentOrElse(
                                r -> {
                                    String serviceName = r.getUri().getHost(); // Service name in Eureka
                                    log.info(FORWARDED_CALL, serviceName, forwardedUri);
                                },
                                () -> log.warn(NO_ROUTE)
                        );
            }));
        };
    }

    private RequestInfo getRequestInfo(ServerWebExchange exchange) {
        String method = exchange.getRequest().getMethod().name();

        Map<String, String> headers = new HashMap<>();
        exchange.getRequest().getHeaders().forEach((key, values) -> headers.put(key, String.join(",", values)));

        Map<String, String> parameters = new HashMap<>();
        exchange.getRequest().getQueryParams().forEach((key, values) -> parameters.put(key, String.join(",", values)));

        return new RequestInfo(method, headers, parameters);
    }

    public static class Config {
    }
}
