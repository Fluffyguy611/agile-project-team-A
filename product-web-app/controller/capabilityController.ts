import { Application, Request, Response } from 'express';
import logger from '../service/logger.js';
import CapabilityService from '../service/capabilityService.js';
import Capability from '../model/capability.js';
import sanitizeHtml from 'sanitize-html';
import multer from 'multer';
import mock from '../common/req.express.session.mock.js';

export default class CapabilityController {
  private capabilityService = new CapabilityService();
  private upload = multer();
  appRoutes(app: Application) {
   
    app.get('/admin/add-capability', async (req: Request, res: Response) => {
      res.render('add-new-capability', {
        role: mock.role,
        isLoggedIn: mock.isLoggedIn});
      });

    app.get('/capability', async (req: Request, res: Response) => {
      let data: Capability[] = [];

      try {
        data = await this.capabilityService.getEveryCapability();
      } catch (e) {
        logger.error(`Couldnt get Capability Leads! Error: ${e}`);
      }

      res.render('list-capability-leads', { capability: data,
        role: mock.role,
        isLoggedIn: mock.isLoggedIn});
    });

    app.post(
      '/admin/add-capability',
      this.upload.single('photo'),
      async (req: Request, res: Response) => {
        let data: Capability = req.body;

        if (!req.file) {
          logger.error('No photo attached!');
        }

        data.capability = sanitizeHtml(data.capability).trim();
        data.name = sanitizeHtml(data.name).trim();
        data.photo = Buffer.from(req.file!.buffer).toString('base64');
        data.message = sanitizeHtml(data.message).trim();

        try {
          const newCapability = await this.capabilityService.createCapability(data);
          res.redirect(`/capability`);
        } catch (e: any) {
          logger.warn(e.message);
          res.render('add-new-capability', { capability: data , role: mock.role,
            isLoggedIn: mock.isLoggedIn} );
        }
      },
    );
  }
}
