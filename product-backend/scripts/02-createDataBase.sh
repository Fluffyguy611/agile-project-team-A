mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "02-registration-system-role.sql"
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "02a-populate-database-role.sql"
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "03-registration-system-user.sql"
