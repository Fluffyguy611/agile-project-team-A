import axios from 'axios';
import logger from './logger.js';
import { API } from '../common/constants.js';
import Capability from '../model/capability.js';

export default class CapabilityService {
  async getEveryCapability(): Promise<Capability[]> {
    try {
      const response = await axios.get(API.CAPABILITY);

      return response.data;
    } catch (e) {
      logger.error('Capability Leads not found');
      throw new Error('Capability Leads not found');
    }
  }

  async createCapability(capability: Capability): Promise<Capability> {
    try {
      const response = await axios.post(API.CREATE_CAPABILITY, capability);
      return response.data;
    } catch (e: any) {
      logger.error(`Could not create Capability! Error: ${e.response.data.message}`);
      throw new Error(e.response.data.message);
    }
  }
}
