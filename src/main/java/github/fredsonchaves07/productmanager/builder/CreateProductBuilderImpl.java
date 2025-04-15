package github.fredsonchaves07.productmanager.builder;

import github.fredsonchaves07.productmanager.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CreateProductBuilderImpl implements CreateProductBuilder {

    private String name;

    private String description;

    private BigDecimal price;

    private int stockQuantity;

    public CreateProductBuilderImpl() {
    }

    @Override
    public CreateProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public CreateProductBuilder description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public CreateProductBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public BigDecimal price() {
        return price;
    }

    @Override
    public CreateProductBuilder stockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
        return this;
    }

    @Override
    public int stockQuantity() {
        return stockQuantity;
    }

    @Autowired
    public Product build() {
        return new Product(this);
    }
}
