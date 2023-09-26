mysql -h "$DB_HOST" -D "$DB_NAME" -u "$DB_USERNAME" -p"$DB_PASSWORD" < "01-job-roles.sql"
mysql -h "$DB_HOST" -D "$DB_NAME" -u "$DB_USERNAME" -p"$DB_PASSWORD" < "populate-database-job-roles.sql"
mysql -h "$DB_HOST" -D "$DB_NAME" -u "$DB_USERNAME" -p"$DB_PASSWORD" < "purge-database.sql"