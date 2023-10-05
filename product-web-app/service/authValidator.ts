export default class AuthValidator {
  public validateEmail(email: string): string | null {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@kainos\.com$/;
    if (!emailRegex.test(email)) {
      return 'Email address must include @kainos.com domain';
    }

    return null;
  }

  public validatePassword(password: string): string | null {
    if (password.length < 8 || password.length > 64) {
      return 'Password must be between 8 and 64 characters';
    }

    const uppercaseRegex = /[A-Z]/;
    const lowercaseRegex = /[a-z]/;
    const specialCharRegex = /[!@#$%^&*()\-_+=<>?]/;

    if (!uppercaseRegex.test(password)) {
      return 'Password must contain at least one uppercase letter';
    }

    if (!lowercaseRegex.test(password)) {
      return 'Password must contain at least one lowercase letter';
    }

    if (!specialCharRegex.test(password)) {
      return 'Password must contain at least one special character';
    }

    return null;
  }
}
