import axios from 'axios';
import logger from './logger.js';
import { API } from '../common/constants.js';
import Capability from '../model/capability.js';

export default class CapabilityService {
   async getEveryCapabilityLead(): Promise<Capability[]> {
try {
  const response = await axios.get(API.CAPABILITY);
  return response.data;
}catch (e: any) {
  logger.error(`Could not get Capability! Error: ${e.response.data.message}`);
  throw new Error(e.response.data.message)
}

   }

  async createCapabilityLead(capability: Capability): Promise<Capability> {
    try {
      const response = await axios.post(API.CREATE_CAPABILITY, capability);
      return response.data;   
    } catch (e: any) {
      logger.error(`Could not create Capability! Error: ${e.response.data.message}`);
      throw new Error(e.response.data.message);
    }
  }
}
