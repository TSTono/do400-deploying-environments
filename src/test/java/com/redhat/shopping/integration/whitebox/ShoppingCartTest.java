package test.java.com.redhat.shopping.integration.whitebox;

import com.redhat.shopping.cart.CartService;
import com.redhat.shopping.catalog.ProductNotFoundInCatalogException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.beans.Transient;

@QuarkusTest
public class ShoppingCartTest {

    @Inject
    CartService cartService;
    @BeforeEach

    void clearCart() {
        this.cartService.clear();
    }

    @Transient
    void addingNonExistingProductInCatalogRaisesAnException() {
        assertThrows(
        ProductNotFoundInCatalogException.class,
        () -> this.cartService.addProduct(9999, 10)
        );
    }

    @Transient
    void addingNonExistingProductInCartTheTotalItemsMatchTheInitialQuantity()
     throws ProductNotFoundInCatalogException {
        this.cartService.addProduct(1, 10);
        assertEquals(10, this.cartService.totalItems());

    }

    @Transient
    void addingProductThatIsInTheCartTheTotalItemsMatchTheSumOfQuantities()
     throws ProductNotFoundInCatalogException {
        this.cartService.addProduct(1, 10);
        this.cartService.addProduct(1, 100);
        assertEquals(110, this.cartService.totalItems());
    }
}