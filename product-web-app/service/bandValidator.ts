import Band from '../model/band.js';

export default class JobRoleValidator {
  validateBand(band: Band) {
    if (band.name.length > 64) {
      return 'Name longer than 64 characters';
    } else if (band.name.length < 2) {
        return 'Name shorter than 2 characters';
    }
    return null;
  }
}