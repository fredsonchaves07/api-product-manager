package github.fredsonchaves07.productmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.fredsonchaves07.productmanager.dao.ProductDao;
import github.fredsonchaves07.productmanager.entity.Product;
import github.fredsonchaves07.productmanager.service.ProductDto;
import github.fredsonchaves07.productmanager.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static github.fredsonchaves07.productmanager.factories.entity.ProductFake.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductDao dao;

    @Autowired
    private ProductService service;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
    }

    @Test
    void shouldCreateAProduct() throws Exception {
        ProductDto productDto = createProductDto();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Produto cadastrado com sucesso."))
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.id").isNotEmpty())
                .andExpect(jsonPath("$.payload.name").value(productDto.name()))
                .andExpect(jsonPath("$.payload.description").value(productDto.description()))
                .andExpect(jsonPath("$.payload.price").value(productDto.price()))
                .andExpect(jsonPath("$.payload.stockQuantity").value(productDto.stockQuantity()));
    }

    @Test
    void shouldThrowErrorWhenNameIsNull() throws Exception {
        Product product = createProductFake();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                null, product.description(), product.price(), product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar o cadastro de produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.type").value("NAME_REQUIRED"))
                .andExpect(jsonPath("$.payload.message").value("Nome inválido"))
                .andExpect(jsonPath("$.payload.description").value("O nome do produto é obrigatório."));
    }

    @Test
    void shouldThrowErrorWhenNameIsEmpty() throws Exception {
        Product product = createProductFake();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                null, product.description(), product.price(), product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar o cadastro de produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.type").value("NAME_REQUIRED"))
                .andExpect(jsonPath("$.payload.message").value("Nome inválido"))
                .andExpect(jsonPath("$.payload.description").value("O nome do produto é obrigatório."));
    }

    @Test
    void shouldThrowErrorWhenPriceIsNull() throws Exception {
        Product product = createProductFake();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                product.name(), product.description(), null, product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar o cadastro de produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.type").value("INVALID_PRICE"))
                .andExpect(jsonPath("$.payload.message").value("Preço inválido"))
                .andExpect(jsonPath("$.payload.description").value("O preço do produto deve ser maior ou igual a zero."));
    }

    @Test
    void shouldThrowErrorWhenPriceIsNegative() throws Exception {
        Product product = createProductFake();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                product.name(), product.description(), BigDecimal.valueOf(-1.0), product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar o cadastro de produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.type").value("INVALID_PRICE"))
                .andExpect(jsonPath("$.payload.message").value("Preço inválido"))
                .andExpect(jsonPath("$.payload.description").value("O preço do produto deve ser maior ou igual a zero."));
    }

    @Test
    void shouldThrowErrorWhenStockQuantityIsNegative() throws Exception {
        Product product = createProductFake();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                product.name(), product.description(), product.price(), -1))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar o cadastro de produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.type").value("INVALID_STOCK"))
                .andExpect(jsonPath("$.payload.message").value("Quantidade de estoque inválida"))
                .andExpect(jsonPath("$.payload.description").value("A quantidade em estoque não pode ser negativa."));
    }

    @Test
    void shouldUpdateAProduct() throws Exception {
        Product newProduct = dao.save(createProductFake());
        ProductDto product = createProductDtoFromProduct(createProductFake());
        mockMvc.perform(put("/products/{id}", newProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Produto atualizado com sucesso."))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.path").value("/products/" + newProduct.id()))
                .andExpect(jsonPath("$.payload.id").isNotEmpty())
                .andExpect(jsonPath("$.payload.name").value(product.name()))
                .andExpect(jsonPath("$.payload.description").value(product.description()))
                .andExpect(jsonPath("$.payload.price").value(product.price()))
                .andExpect(jsonPath("$.payload.stockQuantity").value(product.stockQuantity()));
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWithNameIsNull() throws Exception {
        Product newProduct = dao.save(createProductFake());
        ProductDto product = createProductDtoFromProduct(createProductFake());
        mockMvc.perform(put("/products/{id}", newProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                null, product.description(), product.price(), product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a atualização do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + newProduct.id()))
                .andExpect(jsonPath("$.payload.type").value("NAME_REQUIRED"))
                .andExpect(jsonPath("$.payload.message").value("Nome inválido"))
                .andExpect(jsonPath("$.payload.description").value("O nome do produto é obrigatório."));
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWhenNameIsEmpty() throws Exception {
        Product newProduct = dao.save(createProductFake());
        ProductDto product = createProductDtoFromProduct(createProductFake());
        mockMvc.perform(put("/products/{id}", newProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                "", product.description(), product.price(), product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a atualização do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + newProduct.id()))
                .andExpect(jsonPath("$.payload.type").value("NAME_REQUIRED"))
                .andExpect(jsonPath("$.payload.message").value("Nome inválido"))
                .andExpect(jsonPath("$.payload.description").value("O nome do produto é obrigatório."));
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWhenPriceIsNull() throws Exception {
        Product newProduct = dao.save(createProductFake());
        ProductDto product = createProductDtoFromProduct(createProductFake());
        mockMvc.perform(put("/products/{id}", newProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                product.name(), product.description(), null, product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a atualização do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + newProduct.id()))
                .andExpect(jsonPath("$.payload.type").value("INVALID_PRICE"))
                .andExpect(jsonPath("$.payload.message").value("Preço inválido"))
                .andExpect(jsonPath("$.payload.description").value("O preço do produto deve ser maior ou igual a zero."));
    }

    @Test
    void shouldThrowErrorWhenUpdateProductPriceIsNegative() throws Exception {
        Product newProduct = dao.save(createProductFake());
        ProductDto product = createProductDtoFromProduct(createProductFake());
        mockMvc.perform(put("/products/{id}", newProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                product.name(), product.description(), BigDecimal.valueOf(-1.0), product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a atualização do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + newProduct.id()))
                .andExpect(jsonPath("$.payload.type").value("INVALID_PRICE"))
                .andExpect(jsonPath("$.payload.message").value("Preço inválido"))
                .andExpect(jsonPath("$.payload.description").value("O preço do produto deve ser maior ou igual a zero."));
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWhenStockQuantityIsNegative() throws Exception {
        Product newProduct = dao.save(createProductFake());
        ProductDto product = createProductDtoFromProduct(createProductFake());
        mockMvc.perform(put("/products/{id}", newProduct.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                product.name(), product.description(), product.price(), -1))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a atualização do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + newProduct.id()))
                .andExpect(jsonPath("$.payload.type").value("INVALID_STOCK"))
                .andExpect(jsonPath("$.payload.message").value("Quantidade de estoque inválida"))
                .andExpect(jsonPath("$.payload.description").value("A quantidade em estoque não pode ser negativa."));
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWithIdInexistent() throws Exception {
        ProductDto product = createProductDtoFromProduct(createProductFake());
        mockMvc.perform(put("/products/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductDto.create(
                                "", product.description(), product.price(), product.stockQuantity()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a atualização do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + 999))
                .andExpect(jsonPath("$.payload.type").value("NOT_FOUND"))
                .andExpect(jsonPath("$.payload.message").value("Produto não encontrado"))
                .andExpect(jsonPath("$.payload.description").value("Produto não encontrado com o ID informado."));
    }

    @Test
    void shouldFindProductById() throws Exception {
        Product product = dao.save(createProductFake());
        mockMvc.perform(get("/products/{id}", product.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Produto consultado com sucesso."))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.path").value("/products/" + product.id()))
                .andExpect(jsonPath("$.payload.id").isNotEmpty())
                .andExpect(jsonPath("$.payload.name").value(product.name()))
                .andExpect(jsonPath("$.payload.description").value(product.description()))
                .andExpect(jsonPath("$.payload.price").value(product.price()))
                .andExpect(jsonPath("$.payload.stockQuantity").value(product.stockQuantity()));
    }

    @Test
    void shouldReturnEmptyWhenFindProductByInexistentId() throws Exception {
        mockMvc.perform(get("/products/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a consulta do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + 999))
                .andExpect(jsonPath("$.payload.type").value("NOT_FOUND"))
                .andExpect(jsonPath("$.payload.message").value("Produto não encontrado"))
                .andExpect(jsonPath("$.payload.description").value("Produto não encontrado com o ID informado."));
    }

    @Test
    void shouldListAllProducts() throws Exception {
        dao.save(createProductFake());
        dao.save(createProductFake());
        dao.save(createProductFake());
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Produtos consultados com sucesso."))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload").isNotEmpty());
    }

    @Test
    void shouldListAllEmtpyProducts() throws Exception {
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Produtos consultados com sucesso."))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload").isEmpty());
    }

    @Test
    void shouldDeleteAProduct() throws Exception {
        Product product = dao.save(createProductFake());
        mockMvc.perform(delete("/products/{id}", product.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.status").value("Produto excluído com sucesso."))
                .andExpect(jsonPath("$.statusCode").value(204))
                .andExpect(jsonPath("$.path").value("/products/" + product.id()))
                .andExpect(jsonPath("$.payload").isEmpty());
    }

    @Test
    void notShouldDeleteAProductWithIdInexistent() throws Exception {
        mockMvc.perform(delete("/products/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar a exclusão do produto."))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.path").value("/products/" + 999))
                .andExpect(jsonPath("$.payload.type").value("NOT_FOUND"))
                .andExpect(jsonPath("$.payload.message").value("Produto não encontrado"))
                .andExpect(jsonPath("$.payload.description").value("Produto não encontrado com o ID informado."));
    }

    @Test
    void shouldDeleteAllProducts() throws Exception {
        dao.save(createProductFake());
        dao.save(createProductFake());
        dao.save(createProductFake());
        mockMvc.perform(delete("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.status").value("Produtos excluídos com sucesso."))
                .andExpect(jsonPath("$.statusCode").value(204))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload").isEmpty());
    }
}
