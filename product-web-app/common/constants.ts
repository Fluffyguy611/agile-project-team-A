export const API_URL = `${process.env.API_URL}`;

export const API = {
  PRODUCTS: '/api/jobRoles',
  GET_JOB_ROLE: (id: number) => `/api/jobRoles/${id}`,
};
