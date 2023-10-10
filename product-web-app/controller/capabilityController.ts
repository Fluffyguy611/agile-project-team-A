import { Application, Request, Response } from 'express';
import logger from '../service/logger.js';
import CapabilityService from '../service/capabilityService.js';
import Capability from '../model/capability.js';
import mock from '../common/req.express.session.mock.js';

export default class CapabilityController {
  private capabilityService = new CapabilityService();

  appRoutes(app: Application) {
    app.get('/capability', async (req: Request, res: Response) => {
      let data: Capability[] = [];

      try {
        data = await this.capabilityService.getEveryCapabilityLead();
      } catch (e) {
        logger.error(`Couldnt get Capability Leads! Error: ${e}`);
      }

      res.render('list-capability-leads', {
        capability: data,
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
      });
    });
  }
}
