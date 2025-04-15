package github.fredsonchaves07.productmanager.builder;

import github.fredsonchaves07.productmanager.entity.Product;

import java.math.BigDecimal;

public interface CreateProductBuilder {

    CreateProductBuilder name(String name);

    String name();

    CreateProductBuilder description(String description);

    String description();

    CreateProductBuilder price(BigDecimal price);

    BigDecimal price();

    CreateProductBuilder stockQuantity(int stockQuantity);

    int stockQuantity();

    Product build();
}
