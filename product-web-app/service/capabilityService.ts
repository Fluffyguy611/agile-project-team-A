import axios from 'axios';
import logger from './logger.js';
import { API } from '../common/constants.js';
import Capability from '../model/capability.js';

export default class CapabilityService {
  async getEveryCapabilityLead(): Promise<Capability[]> {
    try {
      const response = await axios.get(API.CAPABILITY);

      return response.data;
    } catch (e) {
      logger.error('Capability Leads not found');
      throw new Error('Capability Leads not found');
    }
  }
}
