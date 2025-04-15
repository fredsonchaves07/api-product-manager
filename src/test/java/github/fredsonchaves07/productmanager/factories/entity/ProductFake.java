package github.fredsonchaves07.productmanager.factories.entity;

import com.github.javafaker.Faker;
import github.fredsonchaves07.productmanager.entity.Product;

import java.math.BigDecimal;
import java.util.Locale;

public final class ProductFake {

    private static final Faker faker = new Faker(Locale.of("pt-BR"));

    public static Product createProductFake() {
        return Product.create()
                .name(faker.commerce().productName())
                .description(faker.lorem().sentence())
                .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price().replace(",", "."))))
                .stockQuantity(faker.number().numberBetween(1, 100))
                .build();
    }
}
