import { expect } from 'chai';
import sinon from 'sinon';
import BandValidator from '../service/bandValidator.js';
import Band from '../model/band.js';
import BandService from '../service/bandService.js';
import logger from '../service/logger.js';
import mockAxios from './axios.instance.test.js';

const bandValidatorStub = sinon.stub(new BandValidator());

const testBandEngi: Band = {
  id: 10,
  name: 'testBandEngi',
  level: 5,
};

const bandService = new BandService(bandValidatorStub);

describe('Band service', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('createNewBand', () => {
    it('When API online expect Band to be created', async () => {
      bandValidatorStub.validateBand.returns(null);

      mockAxios.onPost('/api/admin/band').reply(200, testBandEngi);

      const responseBody = await bandService.createNewBand(testBandEngi);

      expect(responseBody).to.deep.equal(testBandEngi);
      sinon.assert.calledOnceWithExactly(bandValidatorStub.validateBand, testBandEngi);
    });

    it('When Band has invalid fields expect exception', async () => {
      const validationError = 'Name longer than 64 characters';
      bandValidatorStub.validateBand.returns(validationError);
      mockAxios.onPost('mockedApiUrl}/api/admin/band').reply(200, testBandEngi);

      let exception: any;
      try {
        await bandService.createNewBand(testBandEngi);
      } catch (e) {
        exception = e as Error;
      } finally {
        expect(exception.message).to.equal(validationError);
      }
    });
  });
});
