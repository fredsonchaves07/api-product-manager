package github.fredsonchaves07.productmanager.builder;

import github.fredsonchaves07.productmanager.entity.Product;

import java.math.BigDecimal;

public interface UpdateProductBuilder {

    Long id();

    UpdateProductBuilder name(String name);

    String name();

    UpdateProductBuilder description(String description);

    String description();

    UpdateProductBuilder price(BigDecimal price);

    BigDecimal price();

    UpdateProductBuilder stockQuantity(int stockQuantity);

    int stockQuantity();

    Product build();
}
