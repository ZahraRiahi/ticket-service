INSERT INTO user (username, password, role, token)
VALUES ('supporter', 'support123', 'SUPPORTER', '123456789');

INSERT INTO ticket (title, description, status, customer_id, supporter_id)
VALUES ('sample', 'Sample Description', true, 1, 1);