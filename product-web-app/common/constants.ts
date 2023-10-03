export const API_URL = `${process.env.API_URL}`;

export const API = {
    JOB_ROLES: '/api/job-roles/',
    GET_JOB_ROLE: (id: number) => `api/job-roles/${id}`,
    REGISTER: '/api/auth/register'
 };
