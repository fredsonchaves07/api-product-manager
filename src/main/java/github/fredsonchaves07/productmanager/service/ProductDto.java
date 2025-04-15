package github.fredsonchaves07.productmanager.service;

import github.fredsonchaves07.productmanager.entity.Product;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, String description, BigDecimal price, int stockQuantity) {

    public static ProductDto create(String name, String description, BigDecimal price, int stockQuantity) {
        return new ProductDto(null, name, description, price, stockQuantity);
    }

    public static ProductDto fromEntity(Product product) {
        return new ProductDto(
                product.id(), product.name(), product.description(), product.price(), product.stockQuantity());
    }

    Product toEntity() {
        return Product.create()
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}
