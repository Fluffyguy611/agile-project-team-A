import { Application, Request, Response } from "express";
import JobRole from "../model/jobRoleSingleView.js";
import logger from '../service/logger.js';
import jobRoleSingleViewService from '../service/jobRoleSingleViewService.js';



export default class ProductController {
    private jobRoleSingleViewService = new jobRoleSingleViewService();
  
    appRoutes(app: Application) {
      app.get('/jobRoles/:id', async (req: Request, res: Response) => {
        let data = {};
  
        try {
          data = await this.jobRoleSingleViewService.getJobRoleSpecification(Number.parseInt(req.params.id));
        } catch (e) {
          logger.error(`Couldnt get job Role! Error: ${e}`);
        }
  
        res.render('view-single-jobRole', { jobRole: data });
      });
    }
}