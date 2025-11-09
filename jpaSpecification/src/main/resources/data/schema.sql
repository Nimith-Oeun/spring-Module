CREATE TABLE menu_items
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
