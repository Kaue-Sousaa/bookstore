CREATE TABLE book_authors (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,

    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book_authors_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT fk_book_authors_author FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

CREATE INDEX idx_book_authors_book_id ON book_authors(book_id);
CREATE INDEX idx_book_authors_author_id ON book_authors(author_id);