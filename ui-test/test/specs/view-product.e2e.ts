import productsPage from '../pageobjects/products.page.js';
import viewProductPage from '../pageobjects/view-product.page.js';

describe('View Product Page', async () => {
  before(async () => {
    await productsPage.open();
  });

  it('Go to third product', async () => {
    await productsPage.goToViewProduct(3);
  });

  it('Verify details', async () => {
    const productDetails = await viewProductPage.getProductDetails();
    expect(productDetails).toContain('4');
  });
});
