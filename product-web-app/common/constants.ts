export const API_URL = `${process.env.API_URL}`;

export const API = {
  PRODUCTS: '/api/jobRoles',
  GET_JOBROLES: (id: number) => `/api/jobRoles/${id}`,
};
