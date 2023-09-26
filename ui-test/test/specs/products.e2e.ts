import productsPage from '../pageobjects/products.page.js';

describe('Products Page', async () => {
  before(async () => {
    await productsPage.open();
  });
  it('should more than 3 products', async () => {
    const products = await productsPage.getProducts();
    expect(products.length).toBeGreaterThanOrEqual(3);
  });
});
