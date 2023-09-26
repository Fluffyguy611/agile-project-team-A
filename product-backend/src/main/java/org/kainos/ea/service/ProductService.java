package org.kainos.ea.service;

import org.kainos.ea.db.ProductDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.Product;
import org.kainos.ea.model.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductDao productDao;
    private final ProductValidator productValidator;

    public ProductService(ProductDao productDao, ProductValidator productValidator) {
        this.productDao = productDao;
        this.productValidator = productValidator;
    }

    public Product createProduct(ProductRequest product) throws FailedToCreateProductException, InvalidProductException {
        try {
            String validation = productValidator.isValidProduct(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            return productDao.createProduct(product);
        } catch (SQLException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToCreateProductException();
        }
    }

    public List<Product> getAllProducts() throws FailedToGetProductsException {
        try {
            return productDao.getAllProducts();
        } catch (SQLException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToGetProductsException();
        }
    }

    public Product getProductById(int id) throws FailedToGetProductException, ProductDoesNotExistException {
        try {
            Optional<Product> product = productDao.getProductById(id);

            return product.orElseThrow(ProductDoesNotExistException::new);
        } catch (SQLException e) {
            logger.error("SQL exception! Error: {}", e.getMessage());

            throw new FailedToGetProductException();
        }
    }
}
