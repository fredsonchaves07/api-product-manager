package github.fredsonchaves07.productmanager.dao;

import github.fredsonchaves07.productmanager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
