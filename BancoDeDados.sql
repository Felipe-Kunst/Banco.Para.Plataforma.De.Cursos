Create database PlataformaDecurso;

use PlataformaDecurso;

CREATE TABLE ramal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE material (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_livro VARCHAR(255) NOT NULL
);

CREATE TABLE professor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ramal INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    linkImg VARCHAR(255),
    FOREIGN KEY (id_ramal) REFERENCES ramal(id)
);

CREATE TABLE curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    linkImg VARCHAR(255),
    professor_id INT,
    FOREIGN KEY (professor_id) REFERENCES professor(id)
);

CREATE TABLE aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    linkImg VARCHAR(255)
);

CREATE TABLE aula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    video VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    curso_id INT,
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE aluno_curso (
    aluno_id INT NOT NULL,
    curso_id INT NOT NULL,
    FOREIGN KEY (aluno_id) REFERENCES aluno(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE material_curso (
    material_id INT NOT NULL,
    curso_id INT NOT NULL,
    FOREIGN KEY (material_id) REFERENCES material(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE aula_curso (
    aula_id INT NOT NULL,
    curso_id INT NOT NULL,
    FOREIGN KEY (aula_id) REFERENCES aula(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE professor_curso (
    professor_id INT NOT NULL,
    curso_id INT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES professor(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE INDEX idx_curso_professor_id ON curso(professor_id);
CREATE INDEX idx_curso_titulo ON curso(titulo);

CREATE INDEX idx_aula_curso_id ON aula(curso_id);
CREATE INDEX idx_aula_titulo ON aula(titulo);




INSERT INTO ramal (nome) VALUES ('Tecnologia');
INSERT INTO ramal (nome) VALUES ('Biologia');
INSERT INTO ramal (nome) VALUES ('Matemática');

INSERT INTO material (nome_livro) VALUES ('Introdução à Programação');
INSERT INTO material (nome_livro) VALUES ('Biologia Molecular');
INSERT INTO material (nome_livro) VALUES ('Cálculo Avançado');

INSERT INTO professor (id_ramal, nome, email, senha, linkImg) VALUES (1, 'João Silva', 'joao.silva@exemplo.com', 'senha123', 'img/joao.jpg');
INSERT INTO professor (id_ramal, nome, email, senha, linkImg) VALUES (2, 'Maria Oliveira', 'maria.oliveira@exemplo.com', 'senha456', 'img/maria.jpg');
INSERT INTO professor (id_ramal, nome, email, senha, linkImg) VALUES (3, 'Carlos Souza', 'carlos.souza@exemplo.com', 'senha789', 'img/carlos.jpg');

INSERT INTO curso (titulo, descricao, linkImg, professor_id) VALUES ('Fundamentos da Programação', 'Curso básico de programação.', 'img/programacao.jpg', 1);
INSERT INTO curso (titulo, descricao, linkImg, professor_id) VALUES ('Biologia Molecular', 'Introdução à biologia molecular.', 'img/biologia.jpg', 2);
INSERT INTO curso (titulo, descricao, linkImg, professor_id) VALUES ('Cálculo Avançado', 'Curso avançado de cálculo.', 'img/calculo.jpg', 3);

INSERT INTO aluno (nome, email, senha, linkImg) VALUES ('Ana Clara', 'ana.clara@exemplo.com', 'senha123', 'img/ana.jpg');
INSERT INTO aluno (nome, email, senha, linkImg) VALUES ('Lucas Pereira', 'lucas.pereira@exemplo.com', 'senha456', 'img/lucas.jpg');
INSERT INTO aluno (nome, email, senha, linkImg) VALUES ('Mariana Lima', 'mariana.lima@exemplo.com', 'senha789', 'img/mariana.jpg');

INSERT INTO aula (titulo, video, descricao, curso_id) VALUES ('Introdução à Programação', 'video/intro_programacao.mp4', 'Aula introdutória sobre programação.', 1);
INSERT INTO aula (titulo, video, descricao, curso_id) VALUES ('Conceitos Básicos de Biologia Molecular', 'video/intro_biologia.mp4', 'Aula introdutória sobre biologia molecular.', 2);
INSERT INTO aula (titulo, video, descricao, curso_id) VALUES ('Derivadas e Integrais', 'video/calculo.mp4', 'Aula sobre derivadas e integrais.', 3);

INSERT INTO aluno_curso (aluno_id, curso_id) VALUES (1, 1);
INSERT INTO aluno_curso (aluno_id, curso_id) VALUES (2, 2);
INSERT INTO aluno_curso (aluno_id, curso_id) VALUES (3, 3);

INSERT INTO material_curso (material_id, curso_id) VALUES (1, 1);
INSERT INTO material_curso (material_id, curso_id) VALUES (2, 2);
INSERT INTO material_curso (material_id, curso_id) VALUES (3, 3);

INSERT INTO aula_curso (aula_id, curso_id) VALUES (1, 1);
INSERT INTO aula_curso (aula_id, curso_id) VALUES (2, 2);
INSERT INTO aula_curso (aula_id, curso_id) VALUES (3, 3);

INSERT INTO professor_curso (professor_id, curso_id) VALUES (1, 1);
INSERT INTO professor_curso (professor_id, curso_id) VALUES (2, 2);
INSERT INTO professor_curso (professor_id, curso_id) VALUES (3, 3);
