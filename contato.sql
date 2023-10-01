CREATE DATABASE contatos(
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
telefone VARCHAR(20),
email VARCHAR(100),
sexo CHAR
);

INSERT INTO contatos (nome, telefone, email, sexo) VALUES
('João', '(11) 1234-5678', 'joao@example.com', 'M'),
('Maria', '(22) 9876-5432', 'maria@example.com', 'F'),
('Pedro', '(33) 5555-1234' 'pedro@example.com' 'M'),
('Ana', '(44) 1111-2222', 'ana@example.com', 'F');

UPDATE contatos
SET telefone = '(11) 9999-8888', email = 'joao_novo@example.com' 
WHERE id = 1;

DELETE FROM contatos
WHERE id = 1;

SELECT FROM contatos;

SELECT * FROM contatos WHERE id = 2;
