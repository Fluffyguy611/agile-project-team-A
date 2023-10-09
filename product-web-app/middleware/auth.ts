import { Application, NextFunction, Request, Response} from "express";
import axios from "axios";
import { API } from "../common/constants.js";

export default class AuthMiddleware {  
    appRoutes(app: Application){
        app.use((req: Request, res: Response, next: NextFunction) => {
        if(req.path == '/auth/register'){
            next();
        }
        else if (req.session.token) {
            axios.defaults.headers.common['Authorization'] = req.session.token;
            next();
        } else {
            res.redirect('/auth/login');
        }
     });
    }
}
  