import { expect } from 'chai';
import sinon from 'sinon';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import JobRoleValidator from '../service/jobRoleValidator.js';
import JobRole from '../model/jobRole.js';
import JobRoleService from '../service/jobRoleService.js';
import logger from '../service/logger.js';
import { response } from 'express';

const mockAxios = new MockAdapter(axios);
const jobRoleValidatorStub = sinon.stub(new JobRoleValidator());

const jobRoleTestEngi: JobRole = {
    jobId: 69,
    name: 'TestEngi',
    description: 'TestEngisStuff',
    sharePointLink: 'some sharepoint link',
}

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
        jobRoleValidatorStub.validateJobRole.returns(null);
        mockAxios.onPost('/api/admin/job-roles').reply(500);

        let exception: any;
        try {
            await jobRoleService.createNewJobRole(jobRoleTestEngi)
        } catch (e) {
            exception = e as Error;
        } finally {
            expect(exception.message).to.equal('Could not create Job Role');
        }
    });
})