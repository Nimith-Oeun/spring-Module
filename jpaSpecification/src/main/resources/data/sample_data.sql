-- create table menu_items if not exists
CREATE TABLE IF NOT EXISTS menu_items
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    size        VARCHAR(10),
    price       DECIMAL(10, 2) NOT NULL,
    image       VARCHAR(500),
    category    VARCHAR(50),
    description VARCHAR(500),
    is_active   BOOLEAN   DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



-- -- Insert sample drink items
-- INSERT INTO menu_items (name, size, price, image, category, description, is_active, created_at, updated_at)
-- VALUES ('Iced Coffee', 'M', '3.50', 'https://images.unsplash.com/photo-1461988320302-91bde64fc8e4?w=400', 'DRINK',
--         'Fresh brewed coffee served over ice', true, NOW(), NOW()),
--        ('Hot Chocolate', 'L', '4.00', 'https://images.unsplash.com/photo-1542990253-0d0f5be5f0ed?w=400', 'DRINK',
--         'Rich and creamy hot chocolate', true, NOW(), NOW()),
--        ('Green Tea Latte', 'M', '4.50', 'https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400', 'DRINK',
--         'Smooth green tea with steamed milk', true, NOW(), NOW()),
--        ('Fresh Orange Juice', 'L', '3.00', 'https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400',
--         'DRINK', 'Freshly squeezed orange juice', true, NOW(), NOW()),
--        ('Cappuccino', 'M', '4.25', 'https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400', 'DRINK',
--         'Espresso with steamed milk foam', true, NOW(), NOW()),
--        ('Smoothie Bowl', 'L', '5.50', 'https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=400', 'DRINK',
--         'Mixed fruit smoothie in a bowl', true, NOW(), NOW()),
--        ('Thai Iced Tea', 'M', '3.75', 'https://images.unsplash.com/photo-1571934811356-5cc061b6821f?w=400', 'DRINK',
--         'Traditional Thai tea with milk', true, NOW(), NOW());
--
-- -- Insert sample food items
-- INSERT INTO menu_items (name, size, price, image, category, description, is_active, created_at, updated_at)
-- VALUES ('Grilled Chicken Sandwich', 'L', '8.50', 'https://images.unsplash.com/photo-1553979459-d2229ba7433a?w=400',
--         'FOOD', 'Juicy grilled chicken with fresh vegetables', true, NOW(), NOW()),
--        ('Caesar Salad', 'M', '6.75', 'https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400', 'FOOD',
--         'Fresh romaine lettuce with caesar dressing', true, NOW(), NOW()),
--        ('Margherita Pizza', 'L', '12.00', 'https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=400', 'FOOD',
--         'Classic pizza with tomato, mozzarella, and basil', true, NOW(), NOW()),
--        ('BBQ Pork Ribs', 'L', '15.50', 'https://images.unsplash.com/photo-1544025162-d76694265947?w=400', 'FOOD',
--         'Tender pork ribs with BBQ sauce', true, NOW(), NOW()),
--        ('Beef Burger', 'M', '9.25', 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400', 'FOOD',
--         'Beef patty with cheese and fries', true, NOW(), NOW()),
--        ('Fish and Chips', 'L', '11.00', 'https://images.unsplash.com/photo-1544982503-9f984c14501a?w=400', 'FOOD',
--         'Crispy battered fish with golden chips', true, NOW(), NOW()),
--        ('Chicken Wings', 'M', '7.50', 'https://images.unsplash.com/photo-1527477396000-e27163b481c2?w=400', 'FOOD',
--         'Spicy buffalo chicken wings', true, NOW(), NOW()),
--       ('Pasta Primavera', 'M', '10.50', 'https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5?w=400', 'FOOD',
--         'Fresh pasta with vegetables', true, NOW(), NOW());
--
INSERT INTO menu_items (name, size, price, image, category, description, is_active, created_at, updated_at)
SELECT
    CONCAT('Sample Product ', x),
    CASE MOD(x, 3)
        WHEN 0 THEN 'S'
        WHEN 1 THEN 'M'
        ELSE 'L'
        END,
    ROUND(3.00 + (x * 0.05), 2),
    CONCAT('https://images.unsplash.com/photo-', LPAD(x, 6, '0'), '.jpg?w=400'),
    CASE WHEN MOD(x, 2) = 0 THEN 'FOOD' ELSE 'DRINK' END,
    CONCAT('Auto-generated product number ', x),
    TRUE,
    NOW(),
    NOW()
FROM SYSTEM_RANGE(1, 10000) AS seq(x);

