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

