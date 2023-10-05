import { Application, Request, Response } from 'express';
import User from '../model/user.js';
import AuthService from '../service/authService.js';
import AuthValidator from '../service/authValidator.js';
import logger from '../service/logger.js';


export default class AuthController {
  private authService = new AuthService(new AuthValidator());

  appRoutes(app: Application) {
    app.get('/auth/register', async (req: Request, res: Response) => {
      res.render('register');
    });

    app.post('/register', async (req: Request, res: Response) => {
      const data: User = req.body;
      try {
        await this.authService.register(data, req.body.repeatPassword);
        res.redirect('/auth/login');
      } catch (e: any) {
        res.locals.errormessage = e.message;
        res.render('register', req.body);
      }
    });

    app.get('/auth/login', async (req: Request, res: Response) => {
      res.render('login')
    })

    app.post('/login', async (req: Request, res: Response) => {
      let user: User = req.body;
    
      try {
        await this.authService.login(user, res);
    
        res.redirect('/');
      } catch (e: any) {
        logger.error(`Login failed: ${e.message}`);
        res.locals.errormessage = 'Login failed. Please try again.';  // Set a meaningful error message
        res.render('login');
      }
    });
  }
}
