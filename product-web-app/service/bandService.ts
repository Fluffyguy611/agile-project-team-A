import axios from 'axios';
import Band from '../model/band.js';
import BandValidator from './bandValidator.js';
import logger from './logger.js';
import { API } from '../common/constants.js';

export default class BandService {
  private bandValidator: BandValidator;

  constructor(bandValidator: BandValidator) {
    this.bandValidator = bandValidator;
  }

  async createNewBand(band: Band): Promise<Band> {
    const validateError = this.bandValidator.validateBand(band);
    if (validateError) {
      logger.warn(`VALIDATION ERROR: ${validateError}`);
      throw new Error(validateError);
    }

    try {
      const response = await axios.post(API.ADD_BANDS, band);

      return response.data;
    } catch (e: any) {
      logger.error(`Could not add Band! Error: ${e.response.data.message}`);
      throw new Error(e.response.data.message);
    }
  }

  async getBandSpecification(id: number): Promise<Band> {
    try {
      const response = await axios.get(API.GET_BAND(id));

      return response.data;
    } catch (e) {
      logger.error('Band not found');
      throw new Error('Band not found');
    }
  }

  async getAllJobBands(): Promise<Band[]> {
    try {
      const response = await axios.get(API.BANDS);
      return response.data;
    } catch (e) {
      logger.error('Job bands not found');
      throw new Error('Could not get job bands');
    }
  }
}
