import { Application, Request, Response } from 'express';
import { JobRole } from '../model/jobRole.js';
import JobRolesService from '../service/jobRolesService.js';

export default class JobRolesController {
  private jobRolesService = new JobRolesService();

  initialize(app: Application) {
    app.get('/job-roles', async (req: Request, res: Response) => {
      let data: JobRole[] = [];

      try {
        data = await this.jobRolesService.getJobRoles();
        console.table(data);
      } catch (e) {
        console.error(e);
      }
      res.render('job-roles', { roles: data });
    });
  }
}
