package github.fredsonchaves07.productmanager.handler;

import github.fredsonchaves07.productmanager.controller.ApiErrorPayload;
import github.fredsonchaves07.productmanager.controller.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    private final static Logger logger = Logger.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllExceptions(Exception exception, HttpServletRequest request) {
        logger.error("Erro interno ao processar a requisição: {}", exception.getMessage(), exception);
        ApiErrorPayload errorPayload = ApiErrorPayload.of("INTERNAL_ERROR", "Erro interno", "Ocorreu um erro interno no servidor.");
        ApiResponse<ApiErrorPayload> response = ApiResponse.of(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                errorPayload
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
