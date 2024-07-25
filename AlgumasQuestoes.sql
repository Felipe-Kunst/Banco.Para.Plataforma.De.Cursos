-- Questão1 Projete o nome de todos os Ramais (SQL e ÁLgebra Relacional)

π nome(ramal)

SELECT nome FROM ramal;


-- Questão 2 Projete a descrição e título das aulas que tem nome Ambiente de Dados (SQL e ÁLgebra Relacional)

π descricao,titulo(σ titulo= "AmbientedeDados"(aula))

SELECT descricao, titulo FROM aula WHERE titulo = 'Ambiente de Dados';


-- Questão 3  Projete o nome do aluno e a quantidade de cursos em que está matriculado (SQL)

SELECT aluno.nome, COUNT(aluno_curso.curso_id) AS quantidade_cursos
FROM aluno
JOIN aluno_curso ON aluno.id = aluno_curso.aluno_id
GROUP BY aluno.nome;


-- Questão 4 Projete o nome do aluno e o nome dos cursos matriculados (SQL)

SELECT aluno.nome AS nome_aluno, curso.titulo AS nome_curso
FROM aluno
JOIN aluno_curso ON aluno.id = aluno_curso.aluno_id
JOIN curso ON aluno_curso.curso_id = curso.id;


-- Questão 5 Projete o nome do aluno que está matriculado no maior número de cursos (SQL).

SELECT aluno.nome
FROM aluno
JOIN aluno_curso ON aluno.id = aluno_curso.aluno_id
GROUP BY aluno.nome
ORDER BY COUNT(aluno_curso.curso_id) desc Limit 1;


