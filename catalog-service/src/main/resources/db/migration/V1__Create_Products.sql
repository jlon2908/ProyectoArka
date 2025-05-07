-- BRAND
CREATE TABLE brand (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- CATEGORY
CREATE TABLE category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- FEATURE
CREATE TABLE feature (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- PRODUCT
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    unit_price NUMERIC(10,2) NOT NULL,
    brand_id INT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sku VARCHAR(100) UNIQUE,
    CONSTRAINT fk_product_brand FOREIGN KEY (brand_id) REFERENCES brand(id)
);

-- PRODUCT_CATEGORY (tabla intermedia muchos a muchos)
CREATE TABLE product_category (
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    CONSTRAINT fk_pc_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    CONSTRAINT fk_pc_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

-- PRODUCT_FEATURE (tabla intermedia muchos a muchos con valor adicional)
CREATE TABLE product_feature (
    product_id INT NOT NULL,
    feature_id INT NOT NULL,
    attribute_value TEXT NOT NULL,
    PRIMARY KEY (product_id, feature_id),
    CONSTRAINT fk_pf_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    CONSTRAINT fk_pf_feature FOREIGN KEY (feature_id) REFERENCES feature(id) ON DELETE CASCADE
);
