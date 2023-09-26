import axios from 'axios';
import ProductValidator from './productValidator.js';
import Product from '../model/product.js';
import logger from './logger.js';
import { API } from '../common/constants.js';

export default class ProductService {
  private productValidator: ProductValidator;

  constructor(productValidator: ProductValidator) {
    this.productValidator = productValidator;
  }

  async getProducts(): Promise<Product[]> {
    try {
      const response = await axios.get(API.PRODUCTS);

      return response.data;
    } catch (e) {
      logger.error('Could not get products');
      throw new Error('Could not get products');
    }
  }

  async createProduct(product: Product): Promise<Product> {
    const validateError = this.productValidator.validateProduct(product);
    if (validateError) {
      logger.warn(`VALIDATION ERROR: ${validateError}`);
      throw new Error(validateError);
    }

    try {
      const response = await axios.post(API.PRODUCTS, product);

      return response.data;
    } catch (e) {
      logger.error('Could not get products');
      throw new Error('Could not create product');
    }
  }

  async getProductById(id: number): Promise<Product> {
    try {
      const response = await axios.get(API.GET_PRODUCT(id));

      return response.data;
    } catch (e) {
      logger.error('Product not found');
      throw new Error('Product not found');
    }
  }
}
