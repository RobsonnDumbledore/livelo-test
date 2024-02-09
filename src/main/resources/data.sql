-- Limpa os dados existentes para evitar violação da chave primária
DELETE FROM public.order_items;
DELETE FROM public.orders;
DELETE FROM public.product_category;
DELETE FROM public.product;
DELETE FROM public.category;
DELETE FROM public.brand;

-- Insere dados em brand
INSERT INTO public.brand (brand_id, name, active) VALUES
('1384754d-d642-4389-8d81-6e35bb90591a', 'Brand A', true),
('9ca4e3b1-0db7-42cb-8cfb-a268d1d62f54', 'Brand B', true),
('63053168-bebc-4636-9c6a-25466a90a800', 'Brand C', false);

-- Insere dados em category
INSERT INTO public.category (category_id, name, active) VALUES
('178c979d-53d2-4b9c-86a4-3529c87c933b', 'Electronics', true),
('782f68d3-6dea-4728-b726-aa792ae742d3', 'Books', true),
('0a86a5ea-4b0b-4580-9abb-0a754460e6a5', 'Clothing', true);

-- Insere dados em product
INSERT INTO public.product (product_id, product_name, price, active, brand_id) VALUES
('523e1050-1adc-4098-9a70-d4da3c3ea2d6', 'Laptop', 1200.00, true, '1384754d-d642-4389-8d81-6e35bb90591a'),
('3ed752ce-9885-4819-9d59-bdd239fa1ed1', 'E-Reader', 150.00, true, '9ca4e3b1-0db7-42cb-8cfb-a268d1d62f54'),
('4433da03-41af-4ec6-915e-526736e891c1', 'T-Shirt', 20.00, true, '63053168-bebc-4636-9c6a-25466a90a800');

-- Associa produtos e categorias em product_category
INSERT INTO public.product_category (product_id, category_id) VALUES
('523e1050-1adc-4098-9a70-d4da3c3ea2d6', '178c979d-53d2-4b9c-86a4-3529c87c933b'),
('3ed752ce-9885-4819-9d59-bdd239fa1ed1', '782f68d3-6dea-4728-b726-aa792ae742d3'),
('4433da03-41af-4ec6-915e-526736e891c1', '0a86a5ea-4b0b-4580-9abb-0a754460e6a5');

-- Insere dados em orders
INSERT INTO public.orders (order_id, user_id, payment_type, total) VALUES
('14d0b54a-97b8-4527-93c5-9ccaaef7e2eb', 'user1', 'BANK_TRANSFER', 1220.00),
('f48262a5-1680-479f-bff6-0e747dc7b811', 'user2', 'CREDIT_CARD', 170.00);

-- Insere dados em order_items
INSERT INTO public.order_items (order_item_id, order_id, product_id, quantity, price_at_order) VALUES
('9fee70a4-57ff-4eb1-8312-59711365810e', '14d0b54a-97b8-4527-93c5-9ccaaef7e2eb', '523e1050-1adc-4098-9a70-d4da3c3ea2d6', 1, 1200.00),
('9250eb24-034b-4008-8a07-934e8a53a59a', 'f48262a5-1680-479f-bff6-0e747dc7b811', '3ed752ce-9885-4819-9d59-bdd239fa1ed1', 1, 150.00);
