import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.db.ProductDao;
import org.kainos.ea.exception.*;
import org.kainos.ea.model.Product;
import org.kainos.ea.model.ProductRequest;
import org.kainos.ea.service.ProductService;
import org.kainos.ea.service.ProductValidator;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private final ProductDao productDaoMock = mock(ProductDao.class);

    private final ProductValidator productValidatorMock = mock(ProductValidator.class);

    private final ProductService productService = new ProductService(productDaoMock, productValidatorMock);

    private final int MOCKED_PRODUCT_ID = 5;
    private final Product MOCKED_PRODUCT = new Product("MacBook Pro", "Decent computer for hippie", 1499);

    @Test
    void getProductById_When_ProductExist_Expect_ProductToBeReturned() throws ProductDoesNotExistException, FailedToGetProductException, SQLException {
        when(productDaoMock.getProductById(MOCKED_PRODUCT_ID)).thenReturn(Optional.of(MOCKED_PRODUCT));

        Product product = productService.getProductById(MOCKED_PRODUCT_ID);

        assertThat(product).isEqualTo(MOCKED_PRODUCT);
    }

    @Test
    void getProductById_When_NoProductReturned_ProductDoesNotExistExceptionToBeThrown() throws SQLException {
        when(productDaoMock.getProductById(MOCKED_PRODUCT_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ProductDoesNotExistException.class)
                .isThrownBy(() -> productService.getProductById(MOCKED_PRODUCT_ID));
    }

    @Test
    void getProductById_When_ThereIsDatabaseError_Expect_FailedToGetProductExceptionToBeThrown() throws SQLException {
        when(productDaoMock.getProductById(MOCKED_PRODUCT_ID)).thenThrow(SQLException.class);

        assertThatExceptionOfType(FailedToGetProductException.class)
                .isThrownBy(() -> productService.getProductById(MOCKED_PRODUCT_ID));
    }

    @Test
    void getProducts_When_ThereAreProducts_Expect_ProductsToBeReturned() throws SQLException, FailedToGetProductsException {
        List<Product> mockedProducts = List.of(MOCKED_PRODUCT);
        when(productDaoMock.getAllProducts()).thenReturn(mockedProducts);

        List<Product> products = productService.getAllProducts();

        assertThat(products).isEqualTo(mockedProducts);
    }

    @Test
    void getProducts_When_ThereIsDatabaseError_Expect_FailedToGetProductsExceptionToBeThrown() throws SQLException {
        when(productDaoMock.getAllProducts()).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToGetProductsException.class)
                .isThrownBy(() -> productService.getAllProducts())
                .withMessageMatching("Failed to get products from the database");
    }

    @Test
    void createProduct_When_ThereIsValidationError_Expect_InvalidProductExceptionToBeThrown() {
        ProductRequest mockedProductRequest = new ProductRequest("Dell", "Fast computer", 999);
        String mockedValidationError = "Name greater than 75 characters";
        when(productValidatorMock.isValidProduct(mockedProductRequest)).thenReturn(mockedValidationError);

        assertThatExceptionOfType(InvalidProductException.class)
                .isThrownBy(() -> productService.createProduct(mockedProductRequest))
                .withMessageMatching(mockedValidationError);
    }

    @Test
    void createProduct_When_ThereIsDatabaseError_Expect_FailedToCreateProductExceptionToBeThrown() throws SQLException, FailedToCreateProductException {
        ProductRequest mockedProductRequest = new ProductRequest("Dell", "Fast computer", 999);
        when(productDaoMock.createProduct(mockedProductRequest)).thenThrow(new SQLException());

        assertThatExceptionOfType(FailedToCreateProductException.class)
                .isThrownBy(() -> productService.createProduct(mockedProductRequest))
                .withMessageMatching("Failed to create product");
    }

    @Test
    void createProduct_When_ProductInputIsValid_Expect_NewProductToBeReturned() throws SQLException, FailedToCreateProductException, InvalidProductException {
        ProductRequest mockedProductRequest = new ProductRequest("MockedName", "MockedDescription", 999);
        when(productDaoMock.createProduct(mockedProductRequest)).thenReturn(MOCKED_PRODUCT);

        Product newProduct = productService.createProduct(mockedProductRequest);

        assertThat(newProduct).isEqualTo(MOCKED_PRODUCT);
    }
}
