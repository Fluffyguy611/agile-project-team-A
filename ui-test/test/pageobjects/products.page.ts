import Page from './page.js';

class ProductsPage extends Page {
  async getProducts() {
    return $$('tr');
  }

  async goToViewProduct(index: number) {
    return $(`a[href='products/${index}']`).click();
  }
}

export default new ProductsPage();
