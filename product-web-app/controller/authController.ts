import { Application, Request, Response } from "express";
import { User } from "../model/user.js";
import AuthService from '../service/authService.js';
import AuthValidator from "../service/authValidator.js";

export default class AuthController {
  private authService = new AuthService(new AuthValidator());

  appRoutes(app: Application) {
    app.get('/auth/register', async (req: Request, res: Response) => {
      res.render('register');
    });

    app.post('/register', async (req: Request, res: Response) => {
      const data: User = req.body;
      try {
        const newProduct = await this.authService.register(data);
        res.redirect(`/login`);
      } catch (e: any) {
        res.locals.errormessage = e.message;
        res.render('register', req.body);
      }
    });
  };
}
