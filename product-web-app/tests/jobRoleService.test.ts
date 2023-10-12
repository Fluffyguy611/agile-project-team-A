import { expect } from 'chai';
import sinon from 'sinon';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import JobRoleValidator from '../service/jobRoleValidator.js';
import JobRole from '../model/jobRole.js';
import JobRoleService from '../service/jobRoleService.js';
import { API } from '../common/constants.js';
import logger from '../service/logger.js';

const jobRolePrincipal: JobRole = {
  id: 5,
  name: 'Principal',
  description: 'his is a test case',
  sharePointLink: 'https://example.com',
  capabilityId: 1,
  bandId: 1,
};

const jobRoleTestEngi: JobRole = {
  id: 69,
  name: 'TestEngi',
  description: 'TestEngisStuff',
  sharePointLink: 'https://example.com',
  capabilityId: 1,
  bandId: 1,
};

describe('JobRole service', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('createNewJobRole', () => {
    it('When API online expect JobRole to be created', async () => {
      const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());
      const jobRoleService = new JobRoleService(jobRoleValidatorStub);
      const mockAxios = new MockAdapter(axios);

      jobRoleValidatorStub.validateJobRole.returns(null);

      mockAxios.onPost('/api/admin/job-roles', jobRoleTestEngi).reply(200, jobRoleTestEngi);

      const responseBody = await jobRoleService.createNewJobRole(jobRoleTestEngi);

      expect(responseBody).to.deep.equal(jobRoleTestEngi);
      sinon.assert.calledOnceWithExactly(jobRoleValidatorStub.validateJobRole, jobRoleTestEngi);

      mockAxios.restore();
    });

    it('When Job Role has invalid fields expect exception', async () => {
      const validationError = 'Name longer than 64 characters';

      const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());
      const jobRoleService = new JobRoleService(jobRoleValidatorStub);
      const mockAxios = new MockAdapter(axios);

      jobRoleValidatorStub.validateJobRole.returns(validationError);
      mockAxios.onPost('mockedApiUrl}/api/admin/job-roles').reply(200, jobRoleTestEngi);

      let exception: any;
      try {
        await jobRoleService.createNewJobRole(jobRoleTestEngi);
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal(validationError);
      }

      mockAxios.restore();
    });

    describe('getJobRoleById', () => {
      it('when API is down expect exception to be thrown', async () => {
        const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());
        const jobRoleService = new JobRoleService(jobRoleValidatorStub);
        const mockAxios = new MockAdapter(axios);

        mockAxios.onGet('/api/job-roles/1').reply(500);

        let exception: any;
        try {
          await jobRoleService.getJobRoleSpecification(1);
        } catch (e) {
          exception = e as Error;
        } finally {
          expect(exception.message).to.equal('Job Role not found');
        }

        mockAxios.restore();
      });

      it('when jobRole have invalid id expect exception to be thrown', async () => {
        const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());
        const jobRoleService = new JobRoleService(jobRoleValidatorStub);
        const mockAxios = new MockAdapter(axios);

        mockAxios.onGet('/api/job-roles/100000').reply(400);

        let exception: any;
        try {
          await jobRoleService.getJobRoleSpecification(100000);
        } catch (e) {
          exception = e as Error;
        } finally {
          expect(exception.message).to.equal('Job Role not found');
        }

        mockAxios.restore();
      });

      it('when API is online expect jobRole to be returned', async () => {
        const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());
        const jobRoleService = new JobRoleService(jobRoleValidatorStub);
        const mockAxios = new MockAdapter(axios);

        mockAxios.onGet(`/api/job-roles/${jobRolePrincipal.id}`).reply(200, jobRolePrincipal);

        const responseBody = await jobRoleService.getJobRoleSpecification(jobRolePrincipal.id);

        expect(responseBody.id).to.be.equal(jobRolePrincipal.id);

        mockAxios.restore();
      });
    });

    describe('getJobRoles', async () => {
      const data = [
        {
          id: 1,
          name: 'name',
          description: 'description',
          sharePointLink: 'exaple',
          capabilityId: 1,
        },
      ];

      it('when there are job roles expect job roles to be returned', async () => {
        const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());
        const jobRoleService = new JobRoleService(jobRoleValidatorStub);
        const mockAxios = new MockAdapter(axios);
        mockAxios.onGet(API.JOB_ROLES).reply(200, data);

        const result = await jobRoleService.getJobRoles();
        expect(result).to.deep.equal(data);
      });

      it('when Api is down expect exception to be thrown', async () => {
        const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());
        const jobRoleService = new JobRoleService(jobRoleValidatorStub);
        const mockAxios = new MockAdapter(axios);
        mockAxios.onGet(API.JOB_ROLES).reply(500, data);
        let error;

        try {
          await jobRoleService.getJobRoles();
        } catch (e: any) {
          error = e.message;
        }

        expect(error).to.equal('Could not get job roles');
      });
    });
  });
});
