package github.fredsonchaves07.productmanager.service;

import github.fredsonchaves07.productmanager.dao.ProductDao;
import github.fredsonchaves07.productmanager.entity.Product;
import github.fredsonchaves07.productmanager.error.ProductError;
import github.fredsonchaves07.productmanager.error.TypeProductError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static github.fredsonchaves07.productmanager.factories.entity.ProductFake.createProductDtoFromProduct;
import static github.fredsonchaves07.productmanager.factories.entity.ProductFake.createProductFake;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductDao dao;

    @Autowired
    private ProductService service;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
    }

    @Test
    void shouldCreateAProduct() {
        Product newProduct = createProductFake();
        ProductDto product = service.createProduct(createProductDtoFromProduct(newProduct));
        assertNotNull(product.id());
        assertEquals(newProduct.name(), product.name());
        assertEquals(newProduct.description(), product.description());
        assertEquals(newProduct.price(), product.price());
        assertEquals(newProduct.stockQuantity(), product.stockQuantity());
    }

    @Test
    void shouldThrowErrorWhenNameIsNull() {
        TypeProductError expectedTypeError = TypeProductError.NAME_REQUIRED;
        String expectedErrorMessage = "Nome inválido";
        String expectedDescriptionMessage = "O nome do produto é obrigatório.";
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.createProduct(ProductDto.create(
                        null, product.description(), product.price(), product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenNameIsEmpty() {
        TypeProductError expectedTypeError = TypeProductError.NAME_REQUIRED;
        String expectedErrorMessage = "Nome inválido";
        String expectedDescriptionMessage = "O nome do produto é obrigatório.";
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.createProduct(ProductDto.create(
                        "", product.description(), product.price(), product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenPriceIsNull() {
        TypeProductError expectedTypeError = TypeProductError.INVALID_PRICE;
        String expectedErrorMessage = "Preço inválido";
        String expectedDescriptionMessage = "O preço do produto deve ser maior ou igual a zero.";
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.createProduct(ProductDto.create(
                        product.name(), product.description(), null, product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenPriceIsNegative() {
        TypeProductError expectedTypeError = TypeProductError.INVALID_PRICE;
        String expectedErrorMessage = "Preço inválido";
        String expectedDescriptionMessage = "O preço do produto deve ser maior ou igual a zero.";
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.createProduct(ProductDto.create(
                        product.name(), product.description(), BigDecimal.valueOf(-1.0), product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenStockQuantityIsNegative() {
        TypeProductError expectedTypeError = TypeProductError.INVALID_STOCK;
        String expectedErrorMessage = "Quantidade de estoque inválida";
        String expectedDescriptionMessage = "A quantidade em estoque não pode ser negativa.";
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.createProduct(ProductDto.create(
                        product.name(), product.description(), product.price(), -1)));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldUpdateAProduct() {
        Product newProduct = dao.save(createProductFake());
        ProductDto productUpdate = createProductDtoFromProduct(createProductFake());
        ProductDto product = service.updateProduct(newProduct.id(), productUpdate);
        assertEquals(newProduct.id(), product.id());
        assertEquals(productUpdate.name(), product.name());
        assertEquals(productUpdate.description(), product.description());
        assertEquals(productUpdate.price(), product.price());
        assertEquals(productUpdate.stockQuantity(), product.stockQuantity());
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWithNameIsNull() {
        TypeProductError expectedTypeError = TypeProductError.NAME_REQUIRED;
        String expectedErrorMessage = "Nome inválido";
        String expectedDescriptionMessage = "O nome do produto é obrigatório.";
        Product newProduct = dao.save(createProductFake());
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.updateProduct(newProduct.id(), ProductDto.create(
                        null, product.description(), product.price(), product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWhenNameIsEmpty() {
        TypeProductError expectedTypeError = TypeProductError.NAME_REQUIRED;
        String expectedErrorMessage = "Nome inválido";
        String expectedDescriptionMessage = "O nome do produto é obrigatório.";
        Product newProduct = dao.save(createProductFake());
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.updateProduct(newProduct.id(), ProductDto.create(
                        "", product.description(), product.price(), product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWhenPriceIsNull() {
        TypeProductError expectedTypeError = TypeProductError.INVALID_PRICE;
        String expectedErrorMessage = "Preço inválido";
        String expectedDescriptionMessage = "O preço do produto deve ser maior ou igual a zero.";
        Product newProduct = dao.save(createProductFake());
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.updateProduct(newProduct.id(), ProductDto.create(
                        product.name(), product.description(), null, product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenUpdateProductPriceIsNegative() {
        TypeProductError expectedTypeError = TypeProductError.INVALID_PRICE;
        String expectedErrorMessage = "Preço inválido";
        String expectedDescriptionMessage = "O preço do produto deve ser maior ou igual a zero.";
        Product newProduct = dao.save(createProductFake());
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.updateProduct(newProduct.id(), ProductDto.create(
                        product.name(), product.description(), BigDecimal.valueOf(-1.0), product.stockQuantity())));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWhenStockQuantityIsNegative() {
        TypeProductError expectedTypeError = TypeProductError.INVALID_STOCK;
        String expectedErrorMessage = "Quantidade de estoque inválida";
        String expectedDescriptionMessage = "A quantidade em estoque não pode ser negativa.";
        Product newProduct = dao.save(createProductFake());
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.updateProduct(newProduct.id(), ProductDto.create(
                        product.name(), product.description(), product.price(), -1)));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWithIdNull() {
        TypeProductError expectedTypeError = TypeProductError.NOT_FOUND;
        String expectedErrorMessage = "Produto não encontrado";
        String expectedDescriptionMessage = "Produto não encontrado com o ID informado.";
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.updateProduct(null, ProductDto.create(
                        product.name(), product.description(), product.price(), -1)));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldThrowErrorWhenUpdateProductWithIdInexistent() {
        TypeProductError expectedTypeError = TypeProductError.NOT_FOUND;
        String expectedErrorMessage = "Produto não encontrado";
        String expectedDescriptionMessage = "Produto não encontrado com o ID informado.";
        Product product = createProductFake();
        final ProductError productError = assertThrows(
                ProductError.class,
                () -> service.updateProduct(9999L, ProductDto.create(
                        product.name(), product.description(), product.price(), -1)));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldFindProductById() {
        Product newProduct = dao.save(createProductFake());
        Optional<ProductDto> product = service.findProduct(newProduct.id());
        assertTrue(product.isPresent());
        assertEquals(newProduct.id(), product.get().id());
        assertEquals(newProduct.name(), product.get().name());
        assertEquals(newProduct.description(), product.get().description());
        assertEquals(newProduct.price(), product.get().price());
        assertEquals(newProduct.stockQuantity(), product.get().stockQuantity());
    }

    @Test
    void shouldReturnEmptyWhenFindProductByInexistentId() {
        Optional<ProductDto> product = service.findProduct(999L);
        assertTrue(product.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenFindProductByIdNull() {
        Optional<ProductDto> product = service.findProduct(null);
        assertTrue(product.isEmpty());
    }

    @Test
    void shouldListAllProducts() {
        dao.save(createProductFake());
        dao.save(createProductFake());
        dao.save(createProductFake());
        List<ProductDto> products = service.findAllProducts();
        assertFalse(products.isEmpty());
    }

    @Test
    void shouldListAllEmtpyProducts() {
        List<ProductDto> products = service.findAllProducts();
        assertTrue(products.isEmpty());
    }

    @Test
    void shouldDeleteAProduct() {
        Product newProduct = dao.save(createProductFake());
        service.deleteProduct(newProduct.id());
        assertTrue(service.findAllProducts().isEmpty());
    }

    @Test
    void shouldDeleteAllProducts() {
        dao.save(createProductFake());
        dao.save(createProductFake());
        dao.save(createProductFake());
        service.deleteAllProducts();
        assertTrue(service.findAllProducts().isEmpty());
    }

    @Test
    void notShouldDeleteAProductWithIdInexistent() {
        TypeProductError expectedTypeError = TypeProductError.NOT_FOUND;
        String expectedErrorMessage = "Produto não encontrado";
        String expectedDescriptionMessage = "Produto não encontrado com o ID informado.";
        final ProductError productError = assertThrows(ProductError.class, () -> service.deleteProduct(9999L));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }

    @Test
    void shouldNotDeleteProductWithNullId() {
        TypeProductError expectedTypeError = TypeProductError.NOT_FOUND;
        String expectedErrorMessage = "Produto não encontrado";
        String expectedDescriptionMessage = "Produto não encontrado com o ID informado.";
        final ProductError productError = assertThrows(ProductError.class, () -> service.deleteProduct(null));
        assertEquals(expectedTypeError, productError.typeError());
        assertEquals(expectedErrorMessage, productError.message());
        assertEquals(expectedDescriptionMessage, productError.description());
    }
}
