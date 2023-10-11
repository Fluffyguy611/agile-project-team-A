import { Application, NextFunction, Request, Response } from 'express';
import axios from 'axios';

export default class AuthMiddleware {
  appRoutes(app: Application) {
    app.use((req: Request, res: Response, next: NextFunction) => {
      if (req.path.match('/auth')) {
        next();
      } else if (req.cookies.token) {
        axios.defaults.headers.common.Authorization = req.cookies.token;
        app.locals.isAdmin = req.cookies.roleId;
        if (req.path.match('/admin') && req.cookies.roleId !== '1') {
          res.redirect('/job-roles');
        } else {
          next();
        }
      } else {
        res.redirect('/not_found');
      }
    });
  }
}
