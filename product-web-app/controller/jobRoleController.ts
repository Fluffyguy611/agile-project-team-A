import { Application, Request, Response } from 'express';
import sanitizeHtml from 'sanitize-html';
import JobRole from '../model/jobRole.js';
import JobRoleService from '../service/jobRoleService.js';
import JobRoleValidator from '../service/jobRoleValidator.js';
import logger from '../service/logger.js';
import CapabilityService from '../service/capabilityService.js';
import BandService from '../service/bandService.js';
import BandValidator from '../service/bandValidator.js';

export default class JobRoleController {
  private jobRoleService = new JobRoleService(new JobRoleValidator());

  private capabilityService = new CapabilityService();
  private bandService = new BandService(new BandValidator());

  appRoutes(app: Application) {
    app.get('/admin/add-job-roles', async (req: Request, res: Response) => {
      const capabilities = await this.capabilityService.getEveryCapability();
      const bands = await this.bandService.getAllJobBands();
      res.render('add-new-job-role', {
        capabilities,
        bands
      });
    });
    app.post('/admin/add-job-roles', async (req: Request, res: Response) => {
      const data: JobRole = req.body;
      data.name = sanitizeHtml(data.name).trim();
      data.description = sanitizeHtml(data.description).trim();
      data.sharePointLink = sanitizeHtml(data.sharePointLink).trim();
      try {
        const newJobRole = await this.jobRoleService.createNewJobRole(data);
        res.redirect(`/job-roles/${newJobRole.id}`);
      } catch (e: any) {
        logger.warn(e.message);
        res.locals.errorMessage = e.message;
        res.render('add-new-job-role', {
          jobRole: data,
          role: req.session.isAdmin,
        });
      }
    });

    app.get('/job-roles/:id', async (req: Request, res: Response) => {
      let data = {};

      try {
        data = await this.jobRoleService.getJobRoleSpecification(
          Number.parseInt(req.params.id, 10),
        );
      } catch (e) {
        logger.error(`Couldnt get job Role! Error: ${e}`);
      }

      res.render('view-single-jobRole', {
        jobRole: data,
      });
    });

    app.get('/job-roles', async (req: Request, res: Response) => {
      let data: JobRole[] = [];

      try {
        data = await this.jobRoleService.getJobRoles();
      } catch (e) {
        logger.error(`Couldnt get job Role! Error: ${e}`);
      }
      res.render('job-roles', {
        roles: data,
      });
    });
  }
}
