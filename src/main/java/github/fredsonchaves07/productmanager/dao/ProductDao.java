package github.fredsonchaves07.productmanager.dao;

import github.fredsonchaves07.productmanager.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void delete(Product product);

    void deleteAll();

    default int count() {
        return findAll().size();
    }
}
