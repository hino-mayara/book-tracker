-- Inserindo dados iniciais na tabela book
INSERT INTO book (title, ISBN, author, total_pages, pages_read, created_at, updated_at) VALUES
('O Senhor dos Anéis', '978-8578270698', 'J.R.R. Tolkien', 1178, 300, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1984', '978-0451524935', 'George Orwell', 328, 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Duna', '978-0441172719', 'Frank Herbert', 896, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dom Casmurro', '978-8535914849', 'Machado de Assis', 256, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('A Revolução dos Bichos', '978-0452284241', 'George Orwell', 152, 80, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
