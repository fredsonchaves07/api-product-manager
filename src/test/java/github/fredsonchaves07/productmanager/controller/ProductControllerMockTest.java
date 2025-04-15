package github.fredsonchaves07.productmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.fredsonchaves07.productmanager.dao.ProductDao;
import github.fredsonchaves07.productmanager.entity.Product;
import github.fredsonchaves07.productmanager.service.ProductDto;
import github.fredsonchaves07.productmanager.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static github.fredsonchaves07.productmanager.factories.entity.ProductFake.createProductDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductDao dao;

    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnInternalServerErrorWhenRepositoryFails() throws Exception {
        ProductDto productDto = createProductDto();
        when(dao.save(any(Product.class)))
                .thenThrow(new RuntimeException("Database connection failed"));
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar o cadastro de produto."))
                .andExpect(jsonPath("$.statusCode").value(500))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.type").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.payload.message").value("Erro interno"))
                .andExpect(jsonPath("$.payload.description").value("Ocorreu um erro interno no servidor."));
    }

    @Test
    void shouldReturnInternalServerErrorWhenServiceFails() throws Exception {
        ProductDto productDto = createProductDto();
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("Não foi possível realizar o cadastro de produto."))
                .andExpect(jsonPath("$.statusCode").value(500))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.payload.type").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.payload.message").value("Erro interno"))
                .andExpect(jsonPath("$.payload.description").value("Ocorreu um erro interno no servidor."));
    }
}
