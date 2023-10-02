import { expect } from 'chai';
import sinon from 'sinon';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import JobRoleValidator from '../service/jobRoleValidator.js';
import JobRole from '../model/jobRole.js';
import JobRoleService from '../service/jobRoleService.js';
import logger from '../service/logger.js';

const mockAxios = new MockAdapter(axios);
const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());

const jobRolePrincipal: JobRole = {
  id: 5,
  name: 'Principal',
  description: 'his is a test case',
  sharePointLink: 'https://example.com',
};

const jobRoleTestEngi: JobRole = {
  id: 69,
  name: 'TestEngi',
  description: 'TestEngisStuff',
  sharePointLink: 'https://example.com',
};

const jobRoleService = new JobRoleService(jobRoleValidatorStub);

describe('JobRole service', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });
});

describe('createNewJobRole', () => {
  it('When API online expect JobRole to be created', async () => {
    jobRoleValidatorStub.validateJobRole.returns(null);

    mockAxios.onPost('/api/admin/job-roles').reply(200, jobRoleTestEngi);

    const responseBody = await jobRoleService.createNewJobRole(jobRoleTestEngi);

    expect(responseBody).to.deep.equal(jobRoleTestEngi);
    sinon.assert.calledOnceWithExactly(jobRoleValidatorStub.validateJobRole, jobRoleTestEngi);
  });

  it('When Job Role has invalid fields expect exception', async () => {
    const validationError = 'Name longer than 64 characters';
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
  });

  it('When API down expect exception', async () => {

    const jobRoleTestEngi: JobRole = {
      id: 69,
      name: 'TestEngi',
      description: 'TestEngisStuff',
      sharePointLink: 'https://example.com',
    };

    mockAxios.onPost('/api/admin/job-roles/jobRoleTestEngi').reply(500 , {message: 'Could not create Job Role'});
    try {
      const response = await axios.post('/api/admin/job-roles/jobRoleTestEngi', jobRoleTestEngi);

      expect(response.status).to.equal(500);
      expect(response.data).to.have.property('message');
      expect(response.data.message).to.equal('Failed to create Job Role');
    } catch (error) {
      console.error('API request failed:', error);
    }
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
