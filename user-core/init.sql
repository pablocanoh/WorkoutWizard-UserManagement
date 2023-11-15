-- create alert table a load some initial data
create table user (
    id uuid primary key,
    name text,
    email text
);

INSERT INTO user (id, name, email) VALUES (uuid(), 'John Doe', 'johndoe@email.com')