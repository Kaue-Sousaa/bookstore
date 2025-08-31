CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    title VARCHAR(500) NOT NULL,
    subtitle VARCHAR(500),
    description TEXT,
    publisher VARCHAR(255),
    publish_date DATE,
    pages INTEGER,
    language VARCHAR(10) DEFAULT 'pt-BR',
    category_id BIGINT NOT NULL,
    total_copies INTEGER NOT NULL DEFAULT 1,
    available_copies INTEGER NOT NULL DEFAULT 1,
    cover_image_url VARCHAR(1000),
    active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT fk_books_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT chk_books_copies CHECK (total_copies >= 0),
    CONSTRAINT chk_books_available_copies CHECK (available_copies >= 0),
    CONSTRAINT chk_books_available_le_total CHECK (available_copies <= total_copies)
);

CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_category_id ON books(category_id);
CREATE INDEX idx_books_active ON books(active);
CREATE INDEX idx_books_available ON books(available_copies);