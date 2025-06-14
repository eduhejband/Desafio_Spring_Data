-- Tabela Autor
CREATE TABLE autor (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    logradouro VARCHAR(255),
    cidade VARCHAR(255),
    pais VARCHAR(255)
);

-- Tabela Usuario
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela Livro
CREATE TABLE livro (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    ano_publicacao INTEGER,
    autor_principal_id BIGINT REFERENCES autor(id),
    genero VARCHAR(50) NOT NULL
);

-- Tabela de relacionamento Livro-Coautor
CREATE TABLE livro_coautor (
    livro_id BIGINT NOT NULL REFERENCES livro(id),
    autor_id BIGINT NOT NULL REFERENCES autor(id),
    PRIMARY KEY (livro_id, autor_id)
);

-- Tabela Exemplar
CREATE TABLE exemplar (
    id BIGSERIAL PRIMARY KEY,
    codigo_identificacao VARCHAR(100) NOT NULL UNIQUE,
    livro_id BIGINT NOT NULL REFERENCES livro(id)
);

-- Tabela Emprestimo
CREATE TABLE emprestimo (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    exemplar_id BIGINT NOT NULL REFERENCES exemplar(id),
    data_emprestimo DATE NOT NULL,
    data_devolucao_prevista DATE NOT NULL,
    data_devolucao_real DATE,
    status VARCHAR(20) NOT NULL
);

-- Tabela para tags
CREATE TABLE livro_tags (
    livro_id BIGINT NOT NULL REFERENCES livro(id),
    tag VARCHAR(100) NOT NULL,
    PRIMARY KEY (livro_id, tag)
);