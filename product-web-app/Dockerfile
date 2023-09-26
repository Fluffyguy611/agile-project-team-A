FROM node:18-alpine

ARG API_URL
ENV API_URL ${API_URL}

# Faked root tsconfig.json
COPY tsconfig-docker.json tsconfig.json

WORKDIR /app

COPY . /app

RUN npm ci
RUN npm run build


EXPOSE 3000

CMD ["node", "dist/app.js"]