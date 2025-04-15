package github.fredsonchaves07.productmanager.dao;

import github.fredsonchaves07.productmanager.entity.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

import static github.fredsonchaves07.productmanager.factories.entity.ProductFake.createProductFake;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public final class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @TestConfiguration
    static class ProductDaoTestConfig {

        @Bean
        public ProductDao productDao(EntityManager entityManager) {
            ProductDaoImpl dao = new ProductDaoImpl();
            dao.setEntityManager(entityManager);
            return dao;
        }
    }

    @Test
    void shouldCreateAProduct() {
        Product newProduct = createProductFake();
        Product product = productDao.save(newProduct);
        assertNotNull(product.id());
        assertEquals(newProduct.name(), product.name());
        assertEquals(newProduct.description(), product.description());
        assertThat(newProduct.price()).isEqualByComparingTo(product.price());
        assertEquals(newProduct.stockQuantity(), product.stockQuantity());
        assertEquals(Long.valueOf(1), productDao.count());
        assertFalse(productDao.findAll().isEmpty());
    }

    @Test
    void shouldUpdateAProduct() {
        Product product = createProductFake();
        Product newProduct = productDao.save(createProductFake());
        Product productUpdate = productDao.save(newProduct.toBuilder()
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .stockQuantity(product.stockQuantity())
                .build());
        assertEquals(newProduct.id(), productUpdate.id());
        assertEquals(product.name(), productUpdate.name());
        assertEquals(product.description(), productUpdate.description());
        assertThat(product.price()).isEqualByComparingTo(productUpdate.price());
        assertEquals(product.stockQuantity(), productUpdate.stockQuantity());
        assertEquals(Long.valueOf(1), productDao.count());
        assertFalse(productDao.findAll().isEmpty());
    }

    @Test
    void shouldFindProductById() {
        Product newProduct = productDao.save(createProductFake());
        Optional<Product> product = productDao.findById(newProduct.id());
        assertTrue(product.isPresent());
        assertEquals(newProduct.id(), product.get().id());
        assertEquals(newProduct.name(), product.get().name());
        assertEquals(newProduct.description(), product.get().description());
        assertThat(newProduct.price()).isEqualByComparingTo(product.get().price());
        assertEquals(newProduct.stockQuantity(), product.get().stockQuantity());
    }

    @Test
    void shouldReturnEmptyWhenFindProductByInexistentId() {
        Optional<Product> product = productDao.findById(999L);
        assertTrue(product.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenFindProductByIdNull() {
        Optional<Product> product = productDao.findById(null);
        assertTrue(product.isEmpty());
    }

    @Test
    void shouldListAllProducts() {
        productDao.save(createProductFake());
        productDao.save(createProductFake());
        productDao.save(createProductFake());
        List<Product> products = productDao.findAll();
        assertFalse(products.isEmpty());
        assertEquals(Long.valueOf(3), products.size());
    }

    @Test
    void shouldDeleteAProduct() {
        Product newProduct = productDao.save(createProductFake());
        productDao.delete(newProduct);
        assertTrue(productDao.findAll().isEmpty());
        assertEquals(Long.valueOf(0), productDao.count());
    }

    @Test
    void shouldDeleteAllProducts() {
        productDao.save(createProductFake());
        productDao.save(createProductFake());
        productDao.save(createProductFake());
        productDao.deleteAll();
        assertTrue(productDao.findAll().isEmpty());
        assertEquals(Long.valueOf(0), productDao.count());

    }

    @Test
    void shouldNotDeleteProductWithNullId() {
        assertDoesNotThrow(() -> productDao.delete(null));
    }
}
