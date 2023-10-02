import axios from 'axios';
import JobRole from '../model/jobRole.js';
import logger from './logger.js';
import { API } from '../common/constants.js';

export default class JobRoleService {
  async getJobRoleSpecification(id: number): Promise<JobRole> {
    try {
      const response = await axios.get(API.GET_JOB_ROLE(id));

      return response.data;
    } catch (e) {
      logger.error('Job Role not found');
      throw new Error('Job Role not found');
    }
  }

  async getJobRoles(): Promise<JobRole[]> {
    try {
      const response = await axios.get(API.JOB_ROLES);
      return response.data;
    } catch (e) {
      logger.error('Job roles not found');
      throw new Error('Could not get job roles');
    }
  }
}
