import axios from 'axios';
import { User } from '../model/user.js';
import AuthValidator from "./authValidator.js";

export default class AuthService {
    private authValidator: AuthValidator;

    constructor(authValidator: AuthValidator) { 
      this.authValidator = authValidator;
    }

    async register(user: User): Promise<void> {
        const validatePasswordError = this.authValidator.validatePassword(user.password);
        if (validatePasswordError) {
            throw new Error(validatePasswordError);
        }

        const validateEmailError= this.authValidator.validateEmail(user.email);
        if (validateEmailError) {
            throw new Error(validateEmailError);
        }

        try {
            const response = await axios.post('http://localhost:8080/api/auth/register', user);
            console.log('User registered:', response.data);
        } catch (e) {
            throw new Error('Unknown error. Could not register user');
        }
    }
}
