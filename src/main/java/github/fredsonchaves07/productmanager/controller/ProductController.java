package github.fredsonchaves07.productmanager.controller;

import github.fredsonchaves07.productmanager.error.InternalErrorException;
import github.fredsonchaves07.productmanager.error.ProductError;
import github.fredsonchaves07.productmanager.service.ProductDto;
import github.fredsonchaves07.productmanager.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Tag(name = "products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    @Operation(summary = "Criação de um produto", description = "Cadastra um novo produto no sistema.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Produto cadastrado com sucesso."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação do produto."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<ApiResponse<?>> create(@RequestBody ProductDto dto, HttpServletRequest request) {
        try {
            ProductDto product = service.createProduct(dto);
            return success("Produto cadastrado com sucesso.", HttpStatus.CREATED.value(), request.getRequestURI(), product);
        } catch (ProductError error) {
            return error("Não foi possível realizar o cadastro de produto.", HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), error);
        } catch (Exception exception) {
            throw new InternalErrorException("Não foi possível realizar o cadastro de produto.", exception);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualização de produto", description = "Atualiza os dados de um produto existente.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Erro de validação ou produto inexistente."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<ApiResponse<?>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto,
            HttpServletRequest request) {
        try {
            ProductDto product = service.updateProduct(id, productDto);
            return success("Produto atualizado com sucesso.", HttpStatus.OK.value(), request.getRequestURI(), product);
        } catch (ProductError error) {
            return error("Não foi possível realizar a atualização do produto.", HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), error);
        } catch (Exception exception) {
            throw new InternalErrorException("Não foi possível realizar a atualização do produto.", exception);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta de produto por ID", description = "Retorna os dados de um produto com base no ID informado.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produto consultado com sucesso.."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Erro de validação ou produto inexistente."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<ApiResponse<?>> getProduct(
            @PathVariable Long id,
            HttpServletRequest request) {
        try {
            Optional<ProductDto> product = service.findProduct(id);
            return success("Produto consultado com sucesso.", HttpStatus.OK.value(), request.getRequestURI(), product.orElseThrow());
        } catch (ProductError error) {
            return error("Não foi possível realizar a consulta do produto.", HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), error);
        } catch (Exception exception) {
            throw new InternalErrorException("Não foi possível realizar a consulta do produto.", exception);
        }
    }

    @GetMapping
    @Operation(summary = "Consulta de todos os produtos", description = "Retorna a lista completa de produtos cadastrados.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produtos consultados com sucesso."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<ApiResponse<?>> getProducts(HttpServletRequest request) {
        try {
            List<ProductDto> products = service.findAllProducts();
            return success("Produtos consultados com sucesso.", HttpStatus.OK.value(), request.getRequestURI(), products);
        } catch (ProductError error) {
            return error("Não foi possível realizar a consulta de produtos.", HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), error);
        } catch (Exception exception) {
            throw new InternalErrorException("Não foi possível realizar a consulta de produtos.", exception);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclusão de produto por ID", description = "Exclui um produto específico com base no ID informado.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Produto excluído com sucesso."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "ID inválido ou produto inexistente."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable Long id, HttpServletRequest request) {
        try {
            service.deleteProduct(id);
            return success("Produto excluído com sucesso.", HttpStatus.NO_CONTENT.value(), request.getRequestURI(), List.of());
        } catch (ProductError error) {
            return error("Não foi possível realizar a exclusão do produto.", HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), error);
        } catch (Exception exception) {
            throw new InternalErrorException("Não foi possível realizar a exclusão do produto.", exception);
        }
    }

    @DeleteMapping
    @Operation(summary = "Exclusão de todos os produtos", description = "Remove todos os produtos cadastrados no sistema.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Todos os produtos excluídos com sucesso."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    public ResponseEntity<ApiResponse<?>> deleteAll(HttpServletRequest request) {
        try {
            service.deleteAllProducts();
            return success("Produtos excluídos com sucesso.", HttpStatus.NO_CONTENT.value(), request.getRequestURI(), List.of());
        } catch (ProductError error) {
            return error("Não foi possível realizar a exclusão dos produtos.", HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), error);
        } catch (Exception exception) {
            throw new InternalErrorException("Não foi possível realizar a exclusão dos produto.", exception);
        }
    }

    private <T> ResponseEntity<ApiResponse<?>> success(String status, int statusCode, String path, T payload) {
        return ResponseEntity.status(HttpStatus.valueOf(statusCode))
                .body(ApiResponse.of(status, statusCode, path, payload));
    }

    private ResponseEntity<ApiResponse<?>> error(String status, int statusCode, String path, ProductError error) {
        ApiResponse<ApiErrorPayload> response = ApiResponse.of(status, statusCode, path, error);
        return ResponseEntity.status(HttpStatus.valueOf(statusCode)).body(response);
    }
}
