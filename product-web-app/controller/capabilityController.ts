import { Application, Request, Response } from 'express';
import logger from '../service/logger.js';
import CapabilityService from '../service/capabilityService.js';

export default class CapabilityController {
  private capabilityService = new CapabilityService();

  appRoutes(app: Application) {
    app.get('/capability', async (req: Request, res: Response) => {
      let data = {};

      try {
        data = await this.capabilityService.getEveryCapabilityLead();
      } catch (e) {
        logger.error(`Couldnt get Capability Leads! Error: ${e}`);
      }

      res.render('list-capability-leads', { capability: data });
    });
  }

  // Emilia tu twoje
}
