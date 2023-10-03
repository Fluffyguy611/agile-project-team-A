import { Application, Request, Response } from 'express';
import logger from '../service/logger.js';
import JobRoleService from '../service/jobRoleService.js';
import mock from '../common/req.express.session.mock.js';

export default class JobRoleSingleViewController {
  private JobRoleService = new JobRoleService();

  appRoutes(app: Application) {
    app.get('/job-roles/:id', async (req: Request, res: Response) => {
      let data = {};

      try {
        data = await this.JobRoleService.getJobRoleSpecification(
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
  }
}
