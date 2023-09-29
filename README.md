# agile-project - boilerplate

### Prerequirements

- Java 17
- Node 18.x.x
- docker
- docker-compose
- Rancher Desktop
- Google Chrome


### Setup

Environment variables
```
export DB_HOST="mysqldb" # Datatabase password
export DB_NAME="Project" # Datatabase schema projektA_AgnieszkaG
export DB_USERNAME="root" # Datatabase username
export DB_PASSWORD="<db_password>" # Datatabase password
export API_URL="<backend_api_url>" # Backend API url
export WEB_APP_URL="<web_app_url>" # Frontend app url
```

### Docker setup



Start a rancher desktop.

Bulding API image
```bash
docker build ./product-backend --tag product-api
```

Building web-app image
```bash
docker build ./product-web-app --tag product-web-app
```


Starting environment
```bash
docker-compose up
```

Starting environment in the background/detached
```bash
docker-compose up -d
```

Stoping environment
```bash
docker-compose down
```


### Tests

#### Unit tests
```bash
mvn test
```

#### Integration tests 

Start the applications
```bash
docker-compose up -d
```

Run tests
```bash
cd product/backend
mvn verify
```

Stop applications
```bash
docker-compose down
```

#### E2E/UI Tests

```bash
cd ui-test
npm run wdio
```
