import { expect } from 'chai';

import BandValidator from '../service/bandValidator.js';
import Band from '../model/band.js';
import logger from '../service/logger.js';

const bandValidator = new BandValidator();

describe('Band validator', () => {
  before(() => {
    logger.silent();
  });

  after(() => {
    logger.unsilent();
  });

  describe('validateBand', () => {
    it('expect name too long', () => {
      const band: Partial<Band> = {
        name: '123451234512345123451234512345123412345123451234512345123451234512341234512345123451234512345123451234',
      };

      expect(bandValidator.validateBand(band as Band)).to.be.equal(
        'Name longer than 64 characters',
      );
    });

    it('expect no errors', () => {
      const band: Band = {
        id: 99,
        name: 'TestEngiBand',
        level: 5,
      };

      expect(bandValidator.validateBand(band)).to.be.null;


    });

    it('expect Level input to be incorrect', () => {
      const band: Partial<Band> = {
        level: 99,
      };
      expect(bandValidator.validateBandLevel(band as Band)).to.be.null;
    });

    it('expect Level input to be correct', () => {
      const band: Partial<Band> = {
        level: 5,
      };
      expect(bandValidator.validateBandLevel(band as Band)).to.be.equal(
        'Correct Level input',
      );
    });
  });
});
