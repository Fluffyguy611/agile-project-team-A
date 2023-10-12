echo "Populating bands..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "populate-database-job-bands.sql"

echo "Populating roles..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "02a-populate-database-role.sql"

echo "Populating capabilities..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "04a-populate-capability.sql"

echo "Populating job roles..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "populate-database-job-roles.sql"