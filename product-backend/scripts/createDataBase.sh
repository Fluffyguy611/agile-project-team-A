echo "Creating band table..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "02-job-bands.sql"

echo "Creating job roles table..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "01-job-roles.sql"

echo "Creating capability table..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "02-capabilities.sql"


# ------- Authorization -------
echo "Creating role table"
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "02-registration-system-role.sql"

echo "Creating user table..."
mysql -h"$DB_HOST" -D"$DB_NAME" -u"$DB_USERNAME" -p"$DB_PASSWORD" < "03-registration-system-user.sql"

# -----------------------------
