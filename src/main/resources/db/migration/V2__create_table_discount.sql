CREATE TABLE discount_settings (
    id SERIAL PRIMARY KEY,
    payment_type VARCHAR(255) NOT NULL,
    discount_percentage DECIMAL(5, 2) NOT NULL
);

INSERT INTO discount_settings (payment_type, discount_percentage) VALUES ('CREDIT_CARD', 5.00);
INSERT INTO discount_settings (payment_type, discount_percentage) VALUES ('BANK_SLIP', 10.00);
INSERT INTO discount_settings (payment_type, discount_percentage) VALUES ('BANK_TRANSFER', 7.50);
