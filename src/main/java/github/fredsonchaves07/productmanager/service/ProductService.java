package github.fredsonchaves07.productmanager.service;

import github.fredsonchaves07.productmanager.dao.ProductDao;
import github.fredsonchaves07.productmanager.entity.Product;
import github.fredsonchaves07.productmanager.error.ProductError;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static github.fredsonchaves07.productmanager.error.TypeProductError.NOT_FOUND;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        return ProductDto.fromEntity(dao.save(productDto.toEntity()));
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = updateProductWithProductDto(id, productDto);
        dao.save(product);
        return ProductDto.fromEntity(product);
    }

    private Product updateProductWithProductDto(Long id, ProductDto productDto) {
        Product product = dao.findById(id).orElseThrow(() -> ProductError.throwsError(NOT_FOUND));
        return product.toBuilder()
                .name(productDto.name())
                .description(productDto.description())
                .stockQuantity(productDto.stockQuantity())
                .price(productDto.price())
                .build();
    }

    public Optional<ProductDto> findProduct(Long id) {
        Optional<Product> product = dao.findById(id);
        if (product.isEmpty()) throw ProductError.throwsError(NOT_FOUND);
        return product.map(ProductDto::fromEntity);
    }

    public List<ProductDto> findAllProducts() {
        return dao.findAll().stream().map(ProductDto::fromEntity).toList();
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = dao.findById(id).orElseThrow(() -> ProductError.throwsError(NOT_FOUND));
        dao.delete(product);
    }

    @Transactional
    public void deleteAllProducts() {
        dao.deleteAll();
    }
}