package github.fredsonchaves07.productmanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> healthCheck(HttpServletRequest request) {
        final String status = "Api is running!";
        return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.of(
                "Consulta realizado com sucesso", HttpStatus.OK.value(), request.getRequestURI(), HealthStatus.of(status, "1.0.0")));
    }


    @GetMapping("/api/v1/docs")
    public ResponseEntity<Void> redirectToSwagger(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", request.getContextPath() + "/api/v1/swagger-ui/index.html")
                .build();
    }
}
