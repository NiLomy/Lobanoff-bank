CREATE TABLE IF NOT EXISTS passports (id BIGSERIAL PRIMARY KEY
    , name VARCHAR(100) NOT NULL
    , lastname VARCHAR(100) NOT NULL
    , patronymic VARCHAR(100)
    , series SMALLINT NOT NULL
    , number INTEGER NOT NULL
    , birthday DATE NOT NULL
    , gender CHAR(1) NOT NULL
    , department_code CHAR(7) NOT NULL
    , issued_by VARCHAR NOT NULL
    , issued_date DATE NOT NULL
    , address VARCHAR NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY
    , deleted BOOLEAN NOT NULL
    , email VARCHAR NOT NULL
    , password VARCHAR NOT NULL
    , role VARCHAR(50) NOT NULL
    , state VARCHAR(50) NOT NULL
    , passport_id BIGINT REFERENCES passports (id)
    , phone VARCHAR(20)
    , verification_code VARCHAR(128)
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS refresh_tokens (user_id BIGINT PRIMARY KEY
    , token VARCHAR
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS currencies (id SMALLSERIAL PRIMARY KEY
    , iso_code_2 VARCHAR(2) NOT NULL
    , iso_code_3 VARCHAR(3) NOT NULL
    , name VARCHAR NOT NULL
    , icon TEXT
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS account_types (id SMALLSERIAL PRIMARY KEY
    , name VARCHAR(50) not null
    , description VARCHAR
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS accounts (id BIGSERIAL PRIMARY KEY
    , name VARCHAR(100) NOT NULL
    , deposit NUMERIC(28, 8) NOT NULL
    , number VARCHAR(20) NOT NULL
    , main BOOLEAN NOT NULL
    , currency_id SMALLINT NOT NULL
    , type_id SMALLINT NOT NULL
    , owner_id BIGINT NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS requisites (id BIGSERIAL PRIMARY KEY
    , bank_name VARCHAR(100) NOT NULL
    , bic VARCHAR(9) NOT NULL
    , corr_account VARCHAR(20) NOT NULL
    , payee_id BIGINT NOT NULL
    , payee_account_id BIGINT NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS cards (id BIGSERIAL PRIMARY KEY
    , name VARCHAR(100) NOT NULL
    , number VARCHAR(16) NOT NULL
    , cvv VARCHAR(3) not null
    , expiration VARCHAR(5) NOT NULL
    , account_id BIGINT NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE accounts_cards (account_id BIGINT NOT NULL
    , card_id BIGINT NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS card_infos (id BIGSERIAL PRIMARY KEY
    , bin VARCHAR(6) NOT NULL
    , brand VARCHAR(50) NOT NULL
    , type VARCHAR(50) NOT NULL
    , category VARCHAR(50) NOT NULL
    , issuer VARCHAR NOT NULL
    , alpha_2  VARCHAR(2) NOT NULL
    , alpha_3  VARCHAR(3) NOT NULL
    , country  VARCHAR(100) NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS categories (id BIGSERIAL PRIMARY KEY
    , title VARCHAR(50) NOT NULL
    , icon TEXT NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS cashback_categories (id BIGSERIAL PRIMARY KEY
    , category_id BIGINT NOT NULL
    , cashback_percentage NUMERIC(4, 2)
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS transaction_methods (id BIGSERIAL PRIMARY KEY
    , name VARCHAR(50) NOT NULL
    , description VARCHAR
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS transaction_types (id BIGSERIAL PRIMARY KEY
    , name VARCHAR(50) NOT NULL
    , description VARCHAR
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS transactions (id BIGSERIAL PRIMARY KEY
    , init_amount NUMERIC(28, 8) NOT NULL
    , currency_id BIGINT NOT NULL
    , method_id BIGINT NOT NULL
    , type_id BIGINT NOT NULL
    , "from" BIGINT NOT NULL
    , "to" BIGINT NOT NULL
    , bank_name_from VARCHAR(50) NOT NULL
    , bank_name_to VARCHAR(50) NOT NULL
    , date timestamptz NOT NULL
    , cashback NUMERIC(28, 8) NOT NULL
    , commission NUMERIC(28, 8) NOT NULL
    , risk_indicator INTEGER NOT NULL
    , service_company VARCHAR(50) NOT NULL
    , total_amount NUMERIC(28, 8)
    , category_id BIGINT
    , message VARCHAR(150)
    , terminal_ip VARCHAR(50)
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS accounts_transactions (account_id BIGINT NOT NULL
    , transaction_id BIGINT NOT NULL
    , row_change_time timestamptz NOT NULL DEFAULT current_timestamp);

CREATE TABLE IF NOT EXISTS shedlock(name VARCHAR(64) PRIMARY KEY
    , lock_until TIMESTAMP NOT NULL
    , locked_at TIMESTAMP NOT NULL DEFAULT current_timestamp
    , locked_by VARCHAR(255) NOT NULL);
