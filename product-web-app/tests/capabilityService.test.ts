import { expect } from 'chai';
import mockAxios from './axios.instance.test.js';
import logger from '../service/logger.js';
import Capability from '../model/capability.js';
import CapabilityService from '../service/capabilityService.js';
import { API } from '../common/constants.js';

// This sets the mock adapter on the default instance

const capability1: Capability = {
  id: 1,
  capability: 'capability 1',
  name: 'Principal',
  photo:
    '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',
  message: 'sample message',
};

const capability2: Capability = {
  id: 2,
  capability: 'capability 2',
  name: 'Principalllls',
  photo:
    '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',
  message: 'sample message',
};

const capabilityService = new CapabilityService();

describe('Capability service', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('getEveryCapability', () => {
    it('when API is online expect Capabilities to be returned', async () => {
      const data = [capability1, capability2];
      mockAxios.onGet(API.CAPABILITY).reply(200, data);

      const responseBody = await capabilityService.getEveryCapabilityLead();

      expect(responseBody).to.deep.equal(data);
    });

    it('when API is down expect exception to be thrown', async () => {
      mockAxios.onGet(API.CAPABILITY).reply(500);

      let exception: any;
      try {
        await capabilityService.getEveryCapabilityLead();
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal('Capability Leads not found');
      }
    });
  });
});
