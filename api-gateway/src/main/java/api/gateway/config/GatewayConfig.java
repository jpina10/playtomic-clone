package api.gateway.config;

import api.gateway.filter.LoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Configuration
@RestController
@RequiredArgsConstructor
public class GatewayConfig {

    private static final String USER_SERVICE_ROUTE_NAME = "user-service-route";
    private static final String AUTHENTICATION_SERVICE_ROUTE_NAME = "authentication-service-route";
    private static final String USER_URL = "/api/v1/users/**";
    private static final String AUTHENTICATION_URL = "/auth/login";
    private static final String USER_SERVICE_CIRCUIT_BREAKER_NAME = "user-service-circuit-breaker";
    private static final String FALLBACK_URI = "forward:/fallback";
    private static final String LB_USER_SERVICE_URI = "lb://USER-SERVICE";
    private static final String LB_AUTHENTICATION_SERVICE_URI = "lb://AUTHENTICATION-SERVICE";

    private final LoggingFilter loggingFilter;

    @Bean
    public RouteLocator routesLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(USER_SERVICE_ROUTE_NAME, p -> p
                        .path(USER_URL)
                        .filters(f -> f
                                        .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                        .circuitBreaker(config -> config
                                                .setName(USER_SERVICE_CIRCUIT_BREAKER_NAME)
                                                .setFallbackUri(FALLBACK_URI))
                                //.addRequestHeader("headerName", "headerValue")
                        )
                        .uri(LB_USER_SERVICE_URI))
                .route(AUTHENTICATION_SERVICE_ROUTE_NAME, p -> p
                        .path(AUTHENTICATION_URL)
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                        )
                        .uri(LB_AUTHENTICATION_SERVICE_URI))
                .build();
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}
