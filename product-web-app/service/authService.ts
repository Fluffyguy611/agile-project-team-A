import axios from 'axios';
import User from '../model/user.js';
import AuthValidator from './authValidator.js';
import logger from './logger.js';
import { API } from '../common/constants.js';

export default class AuthService {
  private authValidator: AuthValidator;

  constructor(authValidator: AuthValidator) {
    this.authValidator = authValidator;
  }

  async register(user: User, repeatPassword: string): Promise<void> {
    const validatePasswordError = this.authValidator.validatePassword(user.password);
    if (validatePasswordError) {
      logger.warn(`VALIDATION ERROR: ${validatePasswordError}`);
      throw new Error('Provided password is invalid');
    }

    const validateEmailError = this.authValidator.validateEmail(user.email);
    if (validateEmailError) {
      logger.warn(`VALIDATION ERROR: ${validateEmailError}`);
      throw new Error('Provided email is invalid');
    }

    if (user.password !== repeatPassword) {
      logger.warn('VALIDATION ERROR: Password and repeated password do not match');
      throw new Error('Password and repeated password do not match');
    }

    try {
      await axios.post(API.REGISTER, user);
    } catch (e) {
      logger.error('Could not register user');
      throw new Error('Could not register user');
    }
  }
  
  async login(user: User): Promise<string> {
    try {
      const apiResponse = await axios.post(API.LOGIN, user);
      const token = apiResponse.data;

      if (!token) {
        throw new Error('No token found in the response.');
      }

      return token;
    } catch (e: any) {
      logger.error(`Could not login user: ${e.message}`);
      throw new Error('Could not login user');
    }
  }
}