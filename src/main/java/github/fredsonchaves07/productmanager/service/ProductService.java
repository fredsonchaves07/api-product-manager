package github.fredsonchaves07.productmanager.service;

import github.fredsonchaves07.productmanager.dao.ProductDao;
import github.fredsonchaves07.productmanager.entity.Product;
import github.fredsonchaves07.productmanager.error.ProductError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static github.fredsonchaves07.productmanager.error.TypeProductError.NOT_FOUND;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    public ProductDto createProduct(ProductDto productDto) {
        return ProductDto.fromEntity(dao.save(productDto.toEntity()));
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        return ProductDto.fromEntity(updateProductWithProductDto(id, productDto));
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
        return product.map(ProductDto::fromEntity);
    }

    public List<ProductDto> findAllProducts() {
        return dao.findAll().stream().map(ProductDto::fromEntity).toList();
    }

    public void deleteProduct(Long id) {
        Product product = dao.findById(id).orElseThrow(() -> ProductError.throwsError(NOT_FOUND));
        dao.deleteById(id);
    }

    public void deleteAllProducts() {
        dao.deleteAll();
    }
}