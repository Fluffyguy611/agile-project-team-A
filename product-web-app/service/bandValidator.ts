import Band from '../model/band.js';

export default class BandValidator {
  validateBand(band: Band) {
    if (band.name.length > 64) {
      return 'Name longer than 64 characters';
    }
    if (band.name.length < 2) {
      return 'Name shorter than 2 characters';
    }
    return null;
  }

  validateBandLevel(band: Band) {
    if (band.level >= 0 && band.level <= 9) {
      return 'Correct Level input';
    }
    return null;
  }
}
