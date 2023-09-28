export const API_URL = `${process.env.API_URL}`;

export const API = {
  JOBROLES: '/api/admin/job-roles',
  GET_JOBROLE: (jobId: number) => `/api/admin/job-roles/${jobId}`,
};