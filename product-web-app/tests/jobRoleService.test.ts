import { expect } from 'chai';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import JobRole from '../model/jobRole.js';
import logger from '../service/logger.js';
import JobRoleService from '../service/jobRoleService.js';

// This sets the mock adapter on the default instance
const mockAxios = new MockAdapter(axios);

const jobRolePrincipal: JobRole = {
  id: 5,
  name: 'Principal',
  description: 'his is a test case',
  sharePointLink: "https://example.com"
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
    });
});