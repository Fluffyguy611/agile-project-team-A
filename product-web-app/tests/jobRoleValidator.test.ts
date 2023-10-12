import { expect } from 'chai';

import JobRoleValidator from '../service/jobRoleValidator.js';
import JobRole from '../model/jobRole.js';
import logger from '../service/logger.js';

const jobRoleValidator = new JobRoleValidator();

describe('JobRole validator', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('validateJobRole', () => {
    it('expect name too long', () => {
      const jobRole: Partial<JobRole> = {
        name: '123451234512345123451234512345123412345123451234512345123451234512341234512345123451234512345123451234',
      };

      expect(jobRoleValidator.validateJobRole(jobRole as JobRole)).to.be.equal(
        'Name longer than 64 characters',
      );
    });

    it('expect no errors', () => {
      const jobRole: JobRole = {
        id: 99,
        name: 'TestEngi',
        description: 'TestEngiStuff',
        sharePointLink: 'some link',
        capabilityId: 1,
        bandId: 1,
      };

      expect(jobRoleValidator.validateJobRole(jobRole)).to.be.null;
    });
  });
});
