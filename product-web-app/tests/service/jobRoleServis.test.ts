import { expect } from 'chai';
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import JobRolesService from '../../service/jobRolesService.js';

const data = [
  {
    roleId: 1,
    name: 'name',
    description: 'description',
    sharePointLink: 'exaple',
  },
];

it('should return jobRoles from Response', async () => {
  var mock = new MockAdapter(axios);
  const jobRolesService = new JobRolesService();

  mock.onGet('http://localhost:8080/api/roles').reply(200, data);

  var result = await jobRolesService.getJobRoles();

  expect(result).to.deep.equal(data);
});

it('should throw err when  ', async () => {
  var mock = new MockAdapter(axios);
  const jobRolesService = new JobRolesService();

  mock.onGet('http://localhost:8080/api/roles').reply(500, data);
  let error;

  try {
    await jobRolesService.getJobRoles();
  } catch (e: any) {
    error = e.message;
  }

  expect(error).to.equal('Could not get job roles');
});
