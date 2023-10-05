import express, { Application, Request, Response } from 'express';
import * as url from 'url';
import 'dotenv/config';
import session from 'express-session';
import path from 'path';
import nunjucks from 'nunjucks';
import axios from 'axios';
import logger from './service/logger.js';
import JobRoleController from './controller/jobRoleController.js';
import { API_URL } from './common/constants.js';
import AuthController from './controller/authController.js';
import JobRole from './model/jobRole.js';

const dirname = url.fileURLToPath(new URL('.', import.meta.url));

const app: Application = express();

const appViews = path.join(dirname, '/views');

const nunjucksConfig = {
  autoescape: true,
  noCache: true,
  express: app,
};

nunjucks.configure(appViews, nunjucksConfig);

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(session({ secret: 'NOT_HARDCODED_SECRET', cookie: { maxAge: 60000 } }));

axios.defaults.baseURL = API_URL;

declare module 'express-session' {
  interface SessionData {
    jobRole: Partial<JobRole>;
    jobRoleSingleView: JobRole;
  }
}

app.set('view engine', 'html');
app.use('/public', express.static(path.join(dirname, 'public')));

app.listen(3000, () => {
  logger.info('Server listening on port 3000');
});

const authController = new AuthController();

authController.appRoutes(app);

const jobRoleController = new JobRoleController();

jobRoleController.appRoutes(app);

app.get('/', (eq: Request, res: Response) => {
  res.redirect('/job-roles');
});
