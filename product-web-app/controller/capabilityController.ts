import { Application, Request, Response } from 'express';
import logger from '../service/logger.js';
import CapabilityService from '../service/capabilityService.js';

export default class capabilityController {
  private capabilityService = new CapabilityService();

  appRoutes(app: Application) {
    app.get('/capability', async (req: Request, res: Response) => {
      let data = {};

      try {
         await this.capabilityService.getEveryCapabilityLead()
      } catch (e) {
        logger.error(`Couldnt get job Role! Error: ${e}`);
      }

      res.render('view-single-jobRole', { jobRole: data });
    });
  }

  //Emilia tu twoje

}