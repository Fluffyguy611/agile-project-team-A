import { Application, Request, Response } from 'express';
import sanitizeHtml from 'sanitize-html';
import Band from '../model/band.js';
import BandService from '../service/bandService.js';
import BandValidator from '../service/bandValidator.js';
import logger from '../service/logger.js';
import mock from '../common/req.express.session.mock.js';

export default class BandController {
  private bandService = new BandService(new BandValidator());

  appRoutes(app: Application) {
    app.get('/admin/band', async (req: Request, res: Response) => {
      res.render('add-new-band', {
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
      });
    });

    app.post('/admin/band', async (req: Request, res: Response) => {
      const data: Band = req.body;
      data.name = sanitizeHtml(data.name).trim();

      try {
        await this.bandService.createNewBand(data);
        res.redirect('/admin/bands');
      } catch (e: any) {
        logger.warn(e.message);
        res.locals.errorMessage = e.message;
        res.render('add-new-band', {
          Band: data,
          role: mock.role,
          isLoggedIn: mock.isLoggedIn,
        });
      }
    });

    app.get('/band/:id', async (req: Request, res: Response) => {
      let data = {};

      try {
        data = await this.bandService.getBandSpecification(Number.parseInt(req.params.id, 10));
      } catch (e) {
        logger.error(`Couldnt get Band! Error: ${e}`);
      }

      res.render('view-single-band', {
        Band: data,
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
      });
    });

    app.get('/admin/bands', async (req: Request, res: Response) => {
      let data: Band[] = [];

      try {
        data = await this.bandService.getAllJobBands();
      } catch (e) {
        logger.error(`Couldnt get Job Band! Error: ${e}`);
      }
      res.render('view-all-job-bands', {
        Band: data,
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
      });
    });

    app.get('/admin/add-job-roles', async (req: Request, res: Response) => {
      let data: Band[] = [];

      try {
        data = await this.bandService.getAllJobBands();
      } catch (e) {
        logger.error(`Couldnt get Job Band! Error: ${e}`);
      }
      res.render('add-new-job-role', {
        Band: data,
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
      });
    });
  }
}
