-- Insert Table User, password: 12345
INSERT INTO users (id, username, credential, first_name, last_name, created_at, modified_at)
VALUES (1, 'admin', '$2a$10$Hj4cEnXFNuUuVucxMa18ceoYoK.XFh3QwJ.OPsspWFI3jnsw9cx3i', 'Nguyen Van', 'Admin', '2022-12-14 00:00:00.000000', '2022-12-14 00:00:00.000000'),
       (2, 'user', '$2a$10$Hj4cEnXFNuUuVucxMa18ceoYoK.XFh3QwJ.OPsspWFI3jnsw9cx3i', 'Nguyen Van', 'User', '2022-12-14 00:00:00.000000', '2022-12-14 00:00:00.000000');
-- Insert Table Role
INSERT INTO roles (id, role_name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');

-- Insert table Roles_Users
INSERT INTO users_roles (role_id, user_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);

-- Insert Client
INSERT INTO grant_types (id, grant_type) VALUES
    (1, 'AUTHORIZATION_CODE'),
    (2, 'REFRESH_TOKEN'),
    (3, 'CLIENT_CREDENTIALS'),
    (4, 'PASSWORD'),
    (5, 'JWT_BEARER'),
    (6, 'DEVICE_CODE');

INSERT INTO authentication_methods (id, authentication_method) VALUES
    (1, 'CLIENT_SECRET_BASIC'),
    (2, 'CLIENT_SECRET_POST'),
    (3, 'CLIENT_SECRET_JWT'),
    (4, 'PRIVATE_KEY_JWT'),
    (5, 'NONE');

INSERT INTO scopes (id, scope_name) VALUES
    (1, 'OPENID'),
    (2, 'PROFILE'),
    (3, 'EMAIL'),
    (4, 'ADDRESS'),
    (5, 'PHONE');

INSERT INTO clients (id, client_id, client_name, secret, issued_at, expired_time)
VALUES (1, 'client', 'hoang-anh', 'secret', CURRENT_TIMESTAMP, 1800000);

INSERT INTO redirect_uris (id, uri, client_id, is_logout_uri)
VALUES (1, 'https://springone.io/authorized', 1, false),
       (2, 'https://logout-redirect-uri.com', 1, true);

INSERT INTO clients_grant_types (client_id, grant_type_id)
VALUES (1, 1), (1, 2);

INSERT INTO clients_authentication_methods (client_id, authentication_method_id)
VALUES (1, 1);

INSERT INTO clients_scopes (client_id, scope_id)
VALUES (1, 1), (1, 2);


