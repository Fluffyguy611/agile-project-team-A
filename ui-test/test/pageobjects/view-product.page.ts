import Page from './page.js';

class ViewProductPage extends Page {
  public async getProductDetails() {
    return $('#product').getText();
  }
}

export default new ViewProductPage();
