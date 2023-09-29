import JobRole from '../model/jobRole.js';
import axios from 'axios';
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
            logger.warn(`VALIDATION ERROR: $(validateError)`);
            throw new Error(validateError);
        }

        try {
            const response = await axios.post(API.JOB_ROLES, jobRole);
      
            return response.data;
          } catch (e) {
            console.log(e);
            logger.error('Could not get Job Roles');
            throw new Error('Could not create Job Role');
          }
    }
}