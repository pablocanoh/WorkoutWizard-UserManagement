-- create alert table a load some initial data
create table user (
    id uuid primary key,
    created_date timestamp default now() NOT NULL,
    updated_date timestamp default now() NOT NULL,
    state text NOT NULL,
    password text NOT NULL,
    name text NOT NULL,
    email text NOT NULL,

    UNIQUE (email)
);

INSERT INTO user (id, state, password, name, email) VALUES ('00000000-0000-0000-0000-000000000000', 'ACTIVE', 'password', 'admin', 'admin@localhost');
INSERT INTO user (id, state, password, name, email) VALUES ('00000000-0000-0000-0000-000000000001', 'ACTIVE', 'password', 'test user', 'testuser@localhost');
