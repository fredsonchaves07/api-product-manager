package github.fredsonchaves07.productmanager.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Manager API Documentation",
                version = "1.0.0",
                description = "Documentação da API para gerenciamento de produtos.",
                contact = @Contact(
                        name = "Fredson Chaves",
                        email = "fredonchaves07@gmail.com",
                        url = "https://github.com/fredsonchaves07"
                )
        )
)
public class OpenApiConfig {
}
