package github.fredsonchaves07.productmanager.entity;

import github.fredsonchaves07.productmanager.builder.CreateProductBuilder;
import github.fredsonchaves07.productmanager.builder.CreateProductBuilderImpl;
import github.fredsonchaves07.productmanager.builder.UpdateProductBuilder;
import github.fredsonchaves07.productmanager.builder.UpdateProductBuilderImpl;
import github.fredsonchaves07.productmanager.error.ProductError;
import github.fredsonchaves07.productmanager.error.TypeProductError;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public final class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int stockQuantity;

    @Deprecated
    public Product() {
    }

    public Product(CreateProductBuilderImpl builder) {
        this.name = builder.name();
        this.description = builder.description();
        this.price = builder.price();
        this.stockQuantity = builder.stockQuantity();
        this.validate();
    }

    public Product(UpdateProductBuilderImpl builder) {
        this.id = builder.id();
        this.name = builder.name();
        this.description = builder.description();
        this.price = builder.price();
        this.stockQuantity = builder.stockQuantity();
        this.validate();
    }

    public static CreateProductBuilder create() {
        return new CreateProductBuilderImpl();
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public BigDecimal price() {
        return price;
    }

    public int stockQuantity() {
        return stockQuantity;
    }

    public UpdateProductBuilder toBuilder() {
        return new UpdateProductBuilderImpl(this);
    }

    private void validate() {
        if (nameIsNull()) throw ProductError.throwsError(TypeProductError.NAME_REQUIRED);
        if (priceIsNegative()) throw ProductError.throwsError(TypeProductError.INVALID_PRICE);
        if (stockQuantityIsNegative()) throw ProductError.throwsError(TypeProductError.INVALID_STOCK);
    }

    private boolean nameIsNull() {
        return this.name == null || this.name.isEmpty();
    }

    private boolean priceIsNegative() {
        return price == null || price.compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean stockQuantityIsNegative() {
        return stockQuantity < 0;
    }

    @Override
    public String toString() {
        return String.format(
                "Produto [ID: %d, Nome: '%s', Descrição: '%s', Preço: R$ %.2f, Quantidade em Estoque: %d]",
                id,
                name,
                description,
                price,
                stockQuantity);
    }
}
