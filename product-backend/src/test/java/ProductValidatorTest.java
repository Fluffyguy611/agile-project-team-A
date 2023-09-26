import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.model.ProductRequest;
import org.kainos.ea.service.ProductValidator;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductValidatorTest {

    @Mock
    ProductRequest product;

    ProductValidator productValidator = new ProductValidator();

    @Test
    void When_ProductPriceLessThan10_Expect_MessageReturned() {
        when(product.getPrice()).thenReturn(5.3);
        when(product.getName()).thenReturn("Macbook");
        when(product.getDescription()).thenReturn("Ohoho Shiny One!");
        assertThat(productValidator.isValidProduct(product)).isEqualTo("Price less than £10");
    }
}
