CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
insert into roles (id, name) values (uuid_generate_v1(), 'USER'), (uuid_generate_v1(), 'ADMIN') on conflict (name) do nothing;
insert into privileges (id, name) values (uuid_generate_v1(), 'USER'), (uuid_generate_v1(), 'SUPER_ADMIN') on conflict (name) do nothing;
insert into occasions (id, name) values (uuid_generate_v1(), 'Birthday') on conflict (name) do nothing;

