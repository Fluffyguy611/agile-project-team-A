export const API_URL = `${process.env.API_URL}`;

export const API = {
  JOB_ROLES: '/api/jobRoles/',
  GET_JOB_ROLE: (id: number) => `api/jobRoles/${id}`,
};
