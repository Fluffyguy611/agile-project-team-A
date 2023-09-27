import axios from 'axios';
import JobRole from '../model/jobRoleSingleView.js';
import logger from './logger.js';


export default class JobRoleService {

  async getJobRoleSpecification(id: number): Promise<JobRole> {
    try {
      const response = await axios.get('http://localhost:8080/api/jobRoles/' + id,);

      return response.data;
    } catch (e) {
      logger.error('Job Role not found');
      throw new Error('Job Role not found');
    }
  }
}
