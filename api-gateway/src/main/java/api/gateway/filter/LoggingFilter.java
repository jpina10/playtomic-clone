package api.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Log before forwarding the request
            log.info("Received a call to the gateway for: {}", exchange.getRequest().getURI());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Log after forwarding the request
                String forwardedUri = Objects.requireNonNull(exchange.getAttribute("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRequestUrl")).toString();
                log.info("Forwarded call to the User service for: {}", forwardedUri);
            }));
        };
    }

    public static class Config {}
}
