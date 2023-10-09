mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "01-job-roles.sql"
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "02-registration-system-role.sql"
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "03-registration-system-user.sql"
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "04-capability.sql"
