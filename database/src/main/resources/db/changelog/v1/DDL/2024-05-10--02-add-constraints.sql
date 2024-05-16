ALTER TABLE passports
    ADD CONSTRAINT unique_passports_series_and_number
        UNIQUE (series, number);

ALTER TABLE passports
    ADD CONSTRAINT check_passports_birthday
        CHECK (birthday <= now());

ALTER TABLE passports
    ADD CONSTRAINT check_passports_gender
        CHECK (gender IN ('M', 'F'));

ALTER TABLE passports
    ADD CONSTRAINT check_passports_issued_date
        CHECK (issued_date <= now());

ALTER TABLE users
    ADD CONSTRAINT unique_users_email
        UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT unique_users_passport_id
        UNIQUE (passport_id);

ALTER TABLE users
    ADD CONSTRAINT check_users_role
        CHECK ((role)::TEXT = ANY ((ARRAY ['USER'::CHARACTER VARYING, 'ADMIN'::CHARACTER VARYING])::TEXT[]));

ALTER TABLE users
    ADD CONSTRAINT check_users_state
        CHECK ((state)::TEXT = ANY ((ARRAY ['ACTIVE'::CHARACTER VARYING, 'BANNED'::CHARACTER VARYING])::TEXT[]));

ALTER TABLE refresh_tokens
    ADD CONSTRAINT fk_refresh_tokens_users
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE accounts
    ADD CONSTRAINT unique_accounts_number
        UNIQUE (number);

ALTER TABLE accounts
    ADD CONSTRAINT fk_accounts_currencies
        FOREIGN KEY (currency_id) REFERENCES currencies (id) ON DELETE CASCADE;

ALTER TABLE accounts
    ADD CONSTRAINT fk_accounts_types
        FOREIGN KEY (type_id) REFERENCES account_types (id) ON DELETE CASCADE;

ALTER TABLE accounts
    ADD CONSTRAINT fk_accounts_users
        FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE requisites
    ADD CONSTRAINT fk_requisites_users
        FOREIGN KEY (payee_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE requisites
    ADD CONSTRAINT fk_requisites_accounts
        FOREIGN KEY (payee_account_id) REFERENCES accounts (id) ON DELETE CASCADE;

ALTER TABLE cards
    ADD CONSTRAINT fk_cards_accounts
        FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE;

ALTER TABLE accounts_cards
    ADD CONSTRAINT fk_accounts_cards_account
        FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE;

ALTER TABLE accounts_cards
    ADD CONSTRAINT fk_accounts_cards_card
        FOREIGN KEY (card_id) REFERENCES cards (id) ON DELETE CASCADE;

ALTER TABLE cashback_categories
    ADD CONSTRAINT fk_cashback_categories_category
        FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE;

ALTER TABLE transactions
    ADD CONSTRAINT fk_transactions_currencies
        FOREIGN KEY (currency_id) REFERENCES currencies (id) ON DELETE CASCADE;

ALTER TABLE transactions
    ADD CONSTRAINT fk_transactions_methods
        FOREIGN KEY (method_id) REFERENCES transaction_methods (id) ON DELETE CASCADE;

ALTER TABLE transactions
    ADD CONSTRAINT fk_transactions_types
        FOREIGN KEY (type_id) REFERENCES transaction_types (id) ON DELETE CASCADE;

ALTER TABLE transactions
    ADD CONSTRAINT fk_transactions_categories
        FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE;

ALTER TABLE accounts_transactions
    ADD CONSTRAINT fk_accounts_transactions_account
        FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE;

ALTER TABLE accounts_transactions
    ADD CONSTRAINT fk_accounts_transactions_transaction
        FOREIGN KEY (transaction_id) REFERENCES transactions (id) ON DELETE CASCADE;
