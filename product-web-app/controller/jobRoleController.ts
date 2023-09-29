import { Application, Request, Response } from "express";
import JobRole from '../model/jobRole.js';
import JobRoleService from "../service/jobRoleService.js";
import JobRoleValidator from '../service/jobRoleValidator.js';
import logger from "../service/logger.js";
import sanitizeHtml from 'sanitize-html';

export default class JobRoleController {
    private jobRoleService = new JobRoleService(new JobRoleValidator);

    appRoutes(app: Application) {

        app.get('/admin/add-job-roles', async (req: Request, res: Response) => {
            res.render('add-new-job-role');
        });

        app.post('/admin/add-job-roles', async (req: Request, res: Response) => {
            const data: JobRole = req.body;
            data.name = sanitizeHtml(data.name).trim();
            data.description = sanitizeHtml(data.description).trim();
            data.sharePointLink = sanitizeHtml(data.sharePointLink).trim();

            try {
                const newJobRole = await this.jobRoleService.createNewJobRole(data);
                res.redirect(`/admin/job-roles/${newJobRole.jobId}`);
            } catch (e: any) {
                logger.warn(e.message);
                res.locals.errormessage = e.message;
                res.render('add-new-job-role', req.body);
            }
        });
    }
}