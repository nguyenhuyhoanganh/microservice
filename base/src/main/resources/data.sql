-- Insert Table User
INSERT INTO users (id, username, credential, first_name, last_name, created_at, modified_at)
VALUES (1, 'admin', '$2a$10$Hj4cEnXFNuUuVucxMa18ceoYoK.XFh3QwJ.OPsspWFI3jnsw9cx3i', 'Nguyen Van', 'Admin', '2022-12-14 00:00:00.000000', '2022-12-14 00:00:00.000000');
INSERT INTO users (id, username, credential, first_name, last_name, created_at, modified_at)
VALUES (2, 'user', '$2a$10$Hj4cEnXFNuUuVucxMa18ceoYoK.XFh3QwJ.OPsspWFI3jnsw9cx3i', 'Nguyen Van', 'User', '2022-12-14 00:00:00.000000', '2022-12-14 00:00:00.000000');

-- Insert Table Role
INSERT INTO roles (id, role_name)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, role_name)
VALUES (2, 'ROLE_USER');

-- Insert table Roles_Users
INSERT INTO users_roles (role_id, user_id)
VALUES (1, 1);
INSERT INTO users_roles (role_id, user_id)
VALUES (2, 1);
INSERT INTO users_roles (role_id, user_id)
VALUES (2, 2);

-- Insert table Products
INSERT INTO products(id, name, price, image_url, description, modified_by, created_by, modified_at, created_at)
VALUES (1, 'trash', 0, 'https://www.providencejournal.com/gcdn/authoring/2019/12/06/NPRJ/ghows-PJ-9908499a-c755-6b5d-e053-0100007fe9fe-0d7f8468.jpeg', '', 1, 1, '2022-12-14 00:00:00.000000', '2022-12-14 00:00:00.000000')