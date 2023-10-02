import { expect } from 'chai';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import JobRole from '../model/jobRole.js';
import logger from '../service/logger.js';
import JobRoleService from '../service/jobRoleService.js';
import { API } from '../common/constants.js';

const mockAxios = new MockAdapter(axios);

const jobRolePrincipal: JobRole = {
  id: 5,
  name: 'Principal',
  description: 'his is a test case',
  sharePointLink: 'https://example.com',
};

const jobRoleService = new JobRoleService();

describe('JobRole service', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('getJobRoleById', () => {
    it('when API is down expect exception to be thrown', async () => {
      mockAxios.onGet('/api/job-roles/1').reply(500);

      let exception: any;
      try {
        await jobRoleService.getJobRoleSpecification(1);
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal('Job Role not found');
      }
    });

    it('when jobRole have invalid id expect exception to be thrown', async () => {
      mockAxios.onGet('/api/job-roles/100000').reply(400);

      let exception: any;
      try {
        await jobRoleService.getJobRoleSpecification(100000);
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal('Job Role not found');
      }
    });

    it('when API is online expect jobRole to be returned', async () => {
      mockAxios.onGet(`/api/job-roles/${jobRolePrincipal.id}`).reply(200, jobRolePrincipal);

      const responseBody = await jobRoleService.getJobRoleSpecification(jobRolePrincipal.id);

      expect(responseBody.id).to.be.equal(jobRolePrincipal.id);
    });

    const data = [
      {
        id: 1,
        name: 'name',
        description: 'description',
        sharePointLink: 'exaple',
      },
    ];

    it('should return jobRoles from Response', async () => {
      const mock = new MockAdapter(axios);
      const jobRolesService = new JobRoleService();

      mock.onGet(API.JOB_ROLES).reply(200, data);

      const result = await jobRolesService.getJobRoles();
      expect(result).to.deep.equal(data);
    });

    it('should throw err when  ', async () => {
      const mock = new MockAdapter(axios);
      const jobRolesService = new JobRoleService();

      mock.onGet('http://localhost:8080/api/job-roles').reply(500, data);
      let error;

      try {
        await jobRolesService.getJobRoles();
      } catch (e: any) {
        error = e.message;
      }

      expect(error).to.equal('Could not get job roles');
    });
  });
});
