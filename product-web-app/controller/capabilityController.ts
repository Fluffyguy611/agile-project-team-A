import { Application, Request, Response } from 'express';
import sanitizeHtml from 'sanitize-html';
import multer from 'multer';
import logger from '../service/logger.js';
import CapabilityService from '../service/capabilityService.js';
import Capability from '../model/capability.js';

export default class CapabilityController {
  private capabilityService = new CapabilityService();

  private upload = multer();

  appRoutes(app: Application) {
    app.get('/admin/add-capability', async (req: Request, res: Response) => {
      res.render('add-new-capability');
    });

    app.get('/capability', async (req: Request, res: Response) => {
      let data: Capability[] = [];

      try {
        data = await this.capabilityService.getEveryCapability();
      } catch (e) {
        logger.error(`Couldnt get Capability Leads! Error: ${e}`);
      }

      res.render('list-capability-leads', {
        capability: data,
      });
    });

    app.post(
      '/admin/add-capability',
      this.upload.single('photo'),
      async (req: Request, res: Response) => {
        const data: Capability = req.body;

        if (!req.file) {
          logger.error('No photo attached!');
        }

        data.capability = sanitizeHtml(data.capability).trim();
        data.name = sanitizeHtml(data.name).trim();
        data.photo = Buffer.from(req.file!.buffer).toString('base64');
        data.message = sanitizeHtml(data.message).trim();

        try {
          await this.capabilityService.createCapability(data);
          res.redirect('/capability');
        } catch (e: any) {
          logger.warn(e.message);
          res.render('add-new-capability', {
            capability: data,
            errorMessage: e.message
          });
        }
      },
    );
  }
}
