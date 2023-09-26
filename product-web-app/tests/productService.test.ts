import { expect } from 'chai';
import sinon from 'sinon';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import ProductValidator from '../service/productValidator.js';
import Product from '../model/product.js';
import ProductService from '../service/productService.js';
import logger from '../service/logger.js';

// This sets the mock adapter on the default instance
const mockAxios = new MockAdapter(axios);
const productValidatorStub = sinon.stub(new ProductValidator());

const productMacBook: Product = {
  productId: 5,
  name: 'Mac Book',
  description: 'Decent computer',
  price: 1499,
};
const productDell: Product = {
  productId: 5,
  name: 'Dell XPS 13',
  description: 'Good computer',
  price: 999,
};

const productService = new ProductService(productValidatorStub);

describe('Product service', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('getProducts', () => {
    it('when API is online expect products to be returned', async () => {
      mockAxios.onGet('/api/products').reply(200, [productMacBook, productDell]);

      const responseBody = await productService.getProducts();

      expect(responseBody).to.have.lengthOf(2);
      expect(responseBody[0]).to.deep.equal(productMacBook);
    });

    it('when API is down expect exception to be thrown', async () => {
      mockAxios.onGet('/api/products').reply(500);

      let exception: any;
      try {
        await productService.getProducts();
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal('Could not get products');
      }
    });
  });

  describe('createProduct', () => {
    it('when API is online expect products to be created', async () => {
      productValidatorStub.validateProduct.returns(null);

      mockAxios.onPost('/api/products').reply(200, productDell);

      const responseBody = await productService.createProduct(productDell);

      expect(responseBody).to.deep.equal(productDell);
      sinon.assert.calledOnceWithExactly(productValidatorStub.validateProduct, productDell);
    });

    it('when product have invalid fields expect exception to be thrown', async () => {
      const validationError = 'Name greater than 50 characters';
      productValidatorStub.validateProduct.returns(validationError);
      mockAxios.onPost('mockedApiUrl}/api/products').reply(200, productDell);

      let exception: any;
      try {
        await productService.createProduct(productDell);
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal(validationError);
      }
    });

    it('when API is down expect exception to be thrown', async () => {
      productValidatorStub.validateProduct.returns(null);
      mockAxios.onPost('/api/products').reply(500);

      let exception: any;
      try {
        await productService.createProduct(productDell);
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal('Could not create product');
      }
    });
  });

  describe('getProductById', () => {
    it('when API is online expect product to be returned', async () => {
      mockAxios.onGet(`/api/products/${productMacBook.productId}`).reply(200, productMacBook);

      const responseBody = await productService.getProductById(productMacBook.productId);

      expect(responseBody.productId).to.be.equal(productMacBook.productId);
    });

    it('when API is down expect exception to be thrown', async () => {
      const productId = 5;

      mockAxios.onGet(`/api/products/${productId}`).reply(404);

      let exception: any;
      try {
        await productService.getProductById(productId);
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal('Product not found');
      }
    });
  });
});
