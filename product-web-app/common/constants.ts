export const API_URL = `${process.env.API_URL}`;

export const API = {
  JOB_ROLES: '/api/job-roles/',
  GET_JOB_ROLE: (id: number) => `api/job-roles/${id}`,
<<<<<<< HEAD
  CAPABILITY: '/api/capability',
  CREATE_CAPABILITY: '/api/admin/capability',
=======
  ADD_JOB_ROLES: '/api/admin/job-roles',
  REGISTER: '/api/auth/register',
>>>>>>> main
};
