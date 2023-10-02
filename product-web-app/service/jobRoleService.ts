import JobRole from '../model/jobRole.js';
import axios, { AxiosError } from 'axios';
import JobRoleValidator from './jobRoleValidator.js'
import logger from './logger.js';
import { API } from '../common/constants.js';

export default class JobRoleService {
    private jobRoleValidator: JobRoleValidator;

    constructor(jobRoleValidator: JobRoleValidator) {
        this.jobRoleValidator = jobRoleValidator;
    }

    async createNewJobRole(jobRole: JobRole): Promise<JobRole> {
        const validateError = this.jobRoleValidator.validateJobRole(jobRole);
        if (validateError) {
            logger.warn(`VALIDATION ERROR: ${validateError}`);
            throw new Error(validateError);
        }

        try {
            const response = await axios.post(API.ADD_JOB_ROLES, jobRole);
      
            return response.data;
          } catch (e: any) {
            logger.error(`Could not add Job Roles! Error: ${e.response.data.message}`);
            throw new Error(e.response.data.message);
          }
    }

    async getJobRoleSpecification(id: number): Promise<JobRole> {
        try {
          const response = await axios.get(API.GET_JOB_ROLE(id));
    
          return response.data;
        } catch (e) {
          logger.error('Job Role not found');
          throw new Error('Job Role not found');
        }
      }
}
