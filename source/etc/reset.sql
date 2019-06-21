-- Limpa tabelas se existirem
DROP TABLE IF EXISTS emprestimo;
DROP TABLE IF EXISTS disponibilidade;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS livro;
DROP TABLE IF EXISTS categoria;

-- Tabela categoria: representa uma categoria de um livro (aventura, hq...)
CREATE TABLE categoria (
	id_categoria SERIAL NOT NULL PRIMARY KEY,
	nome CHARACTER VARYING(64)
);

-- Tabela livro: representa um livro
CREATE TABLE livro (
	id_livro SERIAL NOT NULL PRIMARY KEY,
	id_categoria INTEGER NOT NULL REFERENCES categoria,
	nome CHARACTER VARYING(64),
	autor CHARACTER VARYING(64),
	foto CHARACTER VARYING(128)
);

-- Tabela usuario: representa as informacoes de login e profile de um usuario
CREATE TABLE usuario (
	id_usuario SERIAL NOT NULL PRIMARY KEY,
	username CHARACTER VARYING(64) NOT NULL,
	password CHARACTER VARYING(64) NOT NULL,
	admin BOOLEAN,

	nome CHARACTER VARYING(64),
	foto CHARACTER VARYING(128)
);

-- Tabela disponibilidade: representa um livro que determinado usuario emprestou para a biblioteca
CREATE TABLE disponibilidade (
	id_disponibilidade SERIAL NOT NULL PRIMARY KEY,
	id_livro INTEGER NOT NULL REFERENCES livro,
	id_usuario INTEGER NOT NULL REFERENCES usuario,
	data_limite DATE
);

-- Tabela emprestimo: representa um emprestimo de livro
CREATE TABLE emprestimo (
    id_emprestimo SERIAL NOT NULL PRIMARY KEY,
	id_disponibilidade INTEGER NOT NULL REFERENCES disponibilidade,
	id_usuario INTEGER NOT NULL REFERENCES usuario,
	status_emprestimo INTEGER NOT NULL, -- 0: emprestimo feito; 1: pegou o livro; 2: entregou o livro
	data_entrega DATE
);

-- Cria usuarios padroes
INSERT INTO usuario (username, password, admin, nome, foto) VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', true, 'Administrador', './images/admin.png');
INSERT INTO usuario (username, password, admin, nome, foto) VALUES ('user', 'ee11cbb19052e40b07aac0ca060c23ee', false, 'Usuario Comum', './images/profile.png');

INSERT INTO categoria (nome) VALUES ('Aventura');
INSERT INTO livro (id_categoria, nome, autor, foto) VALUES (1, 'Harry Potter', 'JK Rowling', './images/unknown.jpeg');