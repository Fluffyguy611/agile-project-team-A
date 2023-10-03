import { expect, assert } from 'chai';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import AuthService from '../service/authService.js';
import AuthValidator from '../service/authValidator.js';
import logger from '../service/logger.js';
import { API } from '../common/constants.js';

const mockAxios = new MockAdapter(axios);

const mockedUser = {
  email: 'username@kainos.com',
  password: 'strongPassword123!',
  roleId: 1,
};

const authService = new AuthService(new AuthValidator());

describe('Auth service', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

describe('AuthService', () => {
  describe('register', () => {
    afterEach(() => {
      mockAxios.reset();
    });

    it('should throw an error for an invalid email', async () => {
      const userWithInvalidEmail = {
        ...mockedUser,
        email: 'invalidemail',
      };

      let error;
      try {
        await authService.register(userWithInvalidEmail, 'strongPassword123!');
      } catch (e: any) {
        error = e;
      }

      expect(error.message).to.equal('Provided email is invalid');
    });

    it('should throw an error for an invalid password', async () => {
      const userWithInvalidPassword = {
        ...mockedUser,
        password: 'weakpassword',
      };

      let error;
      try {
        await authService.register(userWithInvalidPassword, 'weakpassword');
      } catch (e: any) {
        error = e;
      }

      expect(error.message).to.equal('Provided password is invalid');
    });

    it('should throw an error if password and repeatPassword do not match', async () => {
      let error;
      try {
        await authService.register(mockedUser, 'MismatchedPassword');
      } catch (e: any) {
        error = e;
      }

      expect(error.message).to.equal('Password and repeated password do not match');
    });

    it('should register a user when valid email, password, and repeatPassword are provided', async () => {
      mockAxios.onPost(API.REGISTER, mockedUser).reply(200);

      try {
        await authService.register(mockedUser, 'strongPassword123!');
      } catch (e) {
        assert.fail('Couldnt register!');
      }
  
    });
  });
});
});
