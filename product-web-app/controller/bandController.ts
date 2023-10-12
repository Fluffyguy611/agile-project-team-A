import { Application, Request, Response } from 'express';
import sanitizeHtml from 'sanitize-html';
import Band from '../model/band.js';
import BandService from '../service/bandService.js';
import BandValidator from '../service/bandValidator.js';
import logger from '../service/logger.js';

export default class BandController {
  private bandService = new BandService(new BandValidator());

  appRoutes(app: Application) {
    app.get('/admin/band', async (req: Request, res: Response) => {
      res.render('add-new-band');
    });

    app.post('/admin/band', async (req: Request, res: Response) => {
      const data: Band = req.body;
      data.name = sanitizeHtml(data.name).trim();
      console.log(data);
      try {
        await this.bandService.createNewBand(data);
        res.redirect('/');
      } catch (e: any) {
        logger.warn(e.message);
        res.locals.errorMessage = e.message;
        res.render('add-new-band', {
          band: data,
        });
      }
    });
  }
}
