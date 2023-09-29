export const API_URL = `${process.env.API_URL}`;

export const API = {
  JOB_ROLES: '/api/admin/job-roles',
  GET_JOBROLE: (jobId: number) => `/api/admin/job-roles/${jobId}`,
};