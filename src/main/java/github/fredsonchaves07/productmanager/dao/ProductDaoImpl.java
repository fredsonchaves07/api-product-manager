package github.fredsonchaves07.productmanager.dao;

import github.fredsonchaves07.productmanager.entity.Product;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private EntityManager entityManager;

    public ProductDaoImpl() {
    }

    @Override
    public Product save(Product product) {
        if (product.id() == null) return persist(product);
        return merge(product);
    }

    private Product persist(Product product) {
        entityManager.persist(product);
        return product;
    }

    private Product merge(Product product) {
        return entityManager.merge(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    @Override
    public List<Product> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) return;
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void deleteAll() {
        entityManager
                .createQuery("DELETE FROM Product")
                .executeUpdate();
    }

    @Override
    public int count() {
        return entityManager
                .createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult()
                .intValue();
    }
}
