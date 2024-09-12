package user.service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securitySchemeBearer = getBearerSecurityScheme();

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(securitySchemeBearer.getName(), securitySchemeBearer))
                .info(new Info()
                        .title("User microservice")
                        .description("This is a microservice that does CRUD operation in the users")
                        .summary("summary").version("v1"));
    }

    private SecurityScheme getBearerSecurityScheme() {
        SecurityScheme securitySchemeBasic = new SecurityScheme();
        securitySchemeBasic.setName("Bearer Authentication");
        securitySchemeBasic.scheme("bearer");
        securitySchemeBasic.type(SecurityScheme.Type.HTTP);
        securitySchemeBasic.in(SecurityScheme.In.HEADER);
        return securitySchemeBasic;
    }
}
