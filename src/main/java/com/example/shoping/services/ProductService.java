package com.example.shoping.services;

import com.example.shoping.models.Comment;
import com.example.shoping.models.Product;
import com.example.shoping.models.Rate;
import com.example.shoping.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    @Autowired
    public ProductService(UserService userService, ProductRepository productRepository,
                          EntityManager entityManager) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getAllAndPriceBetween(Double min, Double max) {
        if (min == null) min = 0.0d;
        if (max == null) max = Double.MAX_VALUE - 1;

        return entityManager.createQuery(
                        "SELECT p FROM Product p where p.price > :min_price and p.price < :max_price",
                        Product.class)
                .setParameter("min_price", min)
                .setParameter("max_price", max)
                .getResultList();
    }

    public List<Product> getAllByName(String name) {
        return productRepository.findByNameContains(name);
    }

    @Transactional
    public void rateProduct(String username, Long productId, Rate.R r) {
        List<Rate> resultList = entityManager.createQuery(
                        "SELECT r FROM Rate r where r.username = :uName and r.product_id = :pId", Rate.class)
                .setParameter("uName", username)
                .setParameter("pId", productId)
                .getResultList();

        if (resultList.size() == 0) {
            entityManager.createNativeQuery(
                            "insert into rates (username, product_id, rate) VALUES (?, ?, ?)")
                    .setParameter(1, username)
                    .setParameter(2, productId)
                    .setParameter(3, r.name())
                    .executeUpdate();
        } else {
            entityManager.createNativeQuery(
                            "update rates r set rate = ? where r.username = ? and r.product_id = ?")
                    .setParameter(1, r.name())
                    .setParameter(2, username)
                    .setParameter(3, productId)
                    .executeUpdate();
        }
    }

    @Transactional
    public void deleteRate(String username, Long productId) {
        entityManager.createNativeQuery(
                        "DELETE FROM rates r WHERE r.username = ? AND r.product_id = ?")
                .setParameter(1, username)
                .setParameter(2, productId)
                .executeUpdate();
    }

    public void create(Product product) {
        productRepository.save(product);
    }

    public void delete(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepository.delete(product);
        }
    }

    @Transactional
    public void addComment(String username, Long productId, String comment) {
        entityManager.createNativeQuery(
                        "insert into comments (username, product_id, comment) values (?, ?, ?)")
                .setParameter(1, username)
                .setParameter(2, productId)
                .setParameter(3, comment)
                .executeUpdate();
    }

    @Transactional
    public void deleteComment(String username, Long commentId) {
        List<Comment> list = entityManager.createQuery(
                        "SELECT c FROM Comment c where c.id = : cId", Comment.class)
                .setParameter("cId", commentId)
                .getResultList();

        if (list.size() > 0) {
            String uName = list.get(0).getUsername();
            if (uName.equals(username)) {
                entityManager.createNativeQuery(
                                "DELETE FROM comments c WHERE c.id = ?")
                        .setParameter(1, commentId)
                        .executeUpdate();
            }
        }
    }
}
