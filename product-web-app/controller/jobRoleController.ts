import { Application, Request, Response } from 'express';
import sanitizeHtml from 'sanitize-html';
import JobRole from '../model/jobRole.js';
import JobRoleService from '../service/jobRoleService.js';
import mock from '../common/req.express.session.mock.js';
import JobRoleValidator from '../service/jobRoleValidator.js';
import logger from '../service/logger.js';
import CapabilityService from '../service/capabilityService.js';

export default class JobRoleController {
  private jobRoleService = new JobRoleService(new JobRoleValidator());

  private capabilityService = new CapabilityService();

  appRoutes(app: Application) {
    app.get('/admin/add-job-roles', async (req: Request, res: Response) => {
      const capabilities = await this.capabilityService.getEveryCapability();
      res.render('add-new-job-role', {
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
        Capability: capabilities,
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
          role: mock.role,
          isLoggedIn: mock.isLoggedIn,
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
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
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
        role: mock.role,
        isLoggedIn: mock.isLoggedIn,
      });
    });
  }
}
