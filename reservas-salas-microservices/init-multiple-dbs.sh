#!/bin/bash

set -e
set -u

function create_user_and_database() {
	local database=$1
	echo "  Creating user and database '$database'"
	
	# Verifica se o banco já existe
	if psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -lqt | cut -d \| -f 1 | grep -qw $database; then
		echo "  Database '$database' already exists"
	else
		psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
		    CREATE DATABASE $database;
		    GRANT ALL PRIVILEGES ON DATABASE $database TO $POSTGRES_USER;
EOSQL
		echo "  Database '$database' created successfully"
	fi

	# Conecta ao banco e configura as permissões
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -d $database <<-EOSQL
	    GRANT ALL ON SCHEMA public TO $POSTGRES_USER;
	    ALTER DATABASE $database OWNER TO $POSTGRES_USER;
EOSQL
	echo "  Permissions configured for database '$database'"
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
		create_user_and_database $db
	done
	echo "Multiple databases created successfully"
fi 