package github.fredsonchaves07.productmanager.builder;

import github.fredsonchaves07.productmanager.entity.Product;

import java.math.BigDecimal;

public class UpdateProductBuilderImpl implements UpdateProductBuilder {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int stockQuantity;

    public UpdateProductBuilderImpl(Product product) {
        this.id = product.id();
        this.name = product.name();
        this.description = product.description();
        this.price = product.price();
        this.stockQuantity = product.stockQuantity();
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public UpdateProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public UpdateProductBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public UpdateProductBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public BigDecimal price() {
        return price;
    }

    @Override
    public UpdateProductBuilder stockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }

    @Override
    public int stockQuantity() {
        return stockQuantity;
    }

    @Override
    public Product build() {
        return new Product(this);
    }
}
