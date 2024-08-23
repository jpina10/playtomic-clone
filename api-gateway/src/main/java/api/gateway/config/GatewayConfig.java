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

    private final LoggingFilter loggingFilter;

    @Bean
    public RouteLocator routesLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-route", p -> p
                        .path("/api/v1/users/**")
                        .filters(f -> f
                                .filter(loggingFilter.apply(new LoggingFilter.Config()))
                                .addRequestHeader("Hello", "World")
                        )
                        .uri("lb://USER-SERVICE"))
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://httpbin.org:80"))
                .build();
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}
