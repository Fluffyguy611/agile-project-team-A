import { Application, Request, Response } from 'express';
import User from '../model/user.js';
import AuthService from '../service/authService.js';
import AuthValidator from '../service/authValidator.js';
import logger from '../service/logger.js';
import dayjs from 'dayjs';
import UserCredentials from '../model/userCredentials.js';

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
    });

    app.get('/auth/logout', async (req: Request, res: Response) => {
      res.clearCookie('token');
      res.clearCookie('roleId');
      res.redirect('/auth/login');
    })

    app.post('/login', async (req: Request, res: Response) => {
      const user: User = req.body;

      try {
        let credentials: UserCredentials = await this.authService.login(user);
        const expirationTime = dayjs().add(1, 'hour').toDate();
        
        res.cookie('token', credentials.token, { httpOnly: true, expires: expirationTime, maxAge: 3600000 });
        res.cookie('roleId', credentials.roleId, {httpOnly: true, expires: expirationTime, maxAge: 3600000 })
        res.redirect('/');
      } catch (e: any) {
        logger.error(`Login failed: ${e.message}`);
        res.locals.errormessage = 'Login failed. Please try again.';
        res.render('login');
      }
    });
  }
}