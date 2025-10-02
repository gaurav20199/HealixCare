INSERT INTO "users" (uuid,user_name,email, password,is_active)
SELECT '223e4567-e89b-12d3-a456-426614174006', 'test_user','testuser@test.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu',true
    WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE uuid = '223e4567-e89b-12d3-a456-426614174006'
       OR email = 'testuser@test.com'
);

INSERT INTO authority (name)
VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN');

INSERT INTO user_authorities (user_id, authority_id)
VALUES
    (1, 1),
    (1, 2);




