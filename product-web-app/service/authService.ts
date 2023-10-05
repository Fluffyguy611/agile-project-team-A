import axios from 'axios';
import User from '../model/user.js';
import AuthValidator from './authValidator.js';
import logger from './logger.js';
import { API } from '../common/constants.js';
import { Response } from 'express';

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

  async login(user: User, res: Response): Promise<void> {
    const validateEmailError = this.authValidator.validateEmail(user.email);
    if (validateEmailError) {
      logger.warn(`VALIDATION ERROR: ${validateEmailError}`);
      throw new Error("Provided email is invalid");
    }

    try {
      user.roleId = 1; //temporary
      const apiResponse = await axios.post(API.LOGIN, user);

      const token = apiResponse.data;
      console.log('API Response:', token);


      if (token) {
        res.cookie('token', token, { httpOnly: true });
      } else {
        throw new Error('No token found in the response.');
      }
    } catch (e: any) {
      logger.error(`Could not login user: ${e.message}`);
      throw new Error('Could not login user');
    }
}
}
