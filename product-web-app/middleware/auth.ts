import { Application, NextFunction, Request, Response} from "express";
import axios from "axios";

export default class AuthMiddleware {  
    appRoutes(app: Application){
        app.use((req: Request, res: Response, next: NextFunction) => {
        if(req.path == '/auth/register'){
            next();
        }
        else if (req.cookies.token) {
            axios.defaults.headers.common['Authorization'] = req.cookies.token;
            req.session.isAdmin = req.cookies.roleId;
            if (req.path.match('/admin') && req.cookies.roleId != '1'){
                res.redirect('/job-roles');
            }
            else{
                next();
            }
        } else {
            res.redirect('/auth/login');
        }
     });
    }
}
  