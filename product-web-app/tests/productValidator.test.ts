import { expect } from 'chai';

import ProductValidator from '../service/productValidator.js';
import Product from '../model/product.js';
import logger from '../service/logger.js';

const productValidator = new ProductValidator();

describe('Product validator', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('validateProduct', () => {
    it('expect too long length message', () => {
      const product: Partial<Product> = {
        name: 'aa aaa a a a a a a a aa aaa a a a a a a a aa aaa a a a a a a a aa aaa a a a a a a a aa aaa a a a a a a a ',
      };

      expect(productValidator.validateProduct(product as Product)).to.be.equal('Name greater than 50 characters');
    });

    it('expect no errors', () => {
      const product: Product = {
        name: 'Macbook 14 Pro 2021',
        productId: 53,
        description: 'new macbook',
        price: 12,
      };

      expect(productValidator.validateProduct(product)).to.be.null;
    });
  });
});
