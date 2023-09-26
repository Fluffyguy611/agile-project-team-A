export const API_URL = `${process.env.API_URL}`;

export const API = {
  PRODUCTS: '/api/products',
  GET_PRODUCT: (id: number) => `/api/products/${id}`,
};
