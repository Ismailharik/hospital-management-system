package config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Ismail Harik",
                        email = "ismailforevyone@gmail.com",
                        url = "https://harikov.com/home"
                ),
                description = "Open Api Documentation for medicov project",
                title = "Open Api specification - Harikov",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://harikov.com/home"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                description = "Local Env",
                url = "http://localhost:8081"
                ),
                @Server(
                description = "Prod Env",
                url = "http://harikov.com/home"
        )
        },
        security = @SecurityRequirement( // this will allow all endpoints of our project
                name = "bearerAuth"
        )

)
// this bean to allow endpoints on swagger doc to be authorized
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
