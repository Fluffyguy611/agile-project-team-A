export const API_URL = `${process.env.API_URL}`;

export const API = {
  ADD_JOB_ROLES: '/api/admin/job-roles',
  ADD_BANDS: '/api/admin/band',
  JOB_ROLES: '/api/job-roles/',
  BANDS: '/api/admin/bands/',
  GET_JOB_ROLE: (id: number) => `api/job-roles/${id}`,
  GET_BAND: (id: number) => `api/band/${id}`,
};
