import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {

    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Listar Alunos");
            System.out.println("2. Listar Professores");
            System.out.println("3. Listar Professores por Ramal");
            System.out.println("4. Consultar Alunos Matriculados");
            System.out.println("5. Mostrar Quantidade de Aulas");
            System.out.println("6. Listar Ramais");
            System.out.println("7. Listar Cursos e Professores");
            System.out.println("8. Atualizar");
            System.out.println("9. Listar Aulas de Ambiente de Dados");
            System.out.println("10. Listar Nome e Quantidade de Cursos por Aluno");
            System.out.println("11. Listar Nome e Cursos Matriculados por Aluno");
            System.out.println("12. Deletar");
            System.out.println("13. Inserir com Transação");
            System.out.println("14. Inserir sem Transação");
            System.out.println("15. Sair");
            int choice = scanner.nextInt();

            try (Connection conn = getConnection()) {
                switch (choice) {
                    case 1:
                        listarAlunos(conn);
                        break;
                    case 2:
                        listarProfessores(conn);
                        break;
                    case 3:
                        listarProfessorPorRamal(conn);
                        break;
                    case 4:
                        consultarAlunosMatriculados(conn);
                        break;
                    case 5:
                        mostrarQuantidadeAulas(conn);
                        break;
                    case 6:
                        listarRamais(conn);
                        break;
                    case 7:
                        listarCursosEProfessores(conn);
                        break;
                    case 8:
                        atualizar(conn);
                        break;
                    case 9:
                        listarAulasAmbienteDeDados(conn);
                        break;
                    case 10:
                        listarNomeEQuantidadeCursosPorAluno(conn);
                        break;
                    case 11:
                        listarNomeECursosMatriculados(conn);
                        break;
                    case 12:
                        deletar(conn);
                        break;
                    case 13:
                        inserirComTransacao(conn);
                        break;
                    case 14:
                        inserirSemTransacao(conn);
                        break;
                    case 15:
                        System.out.println("Saindo...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void listarAlunos(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM aluno";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            System.out.println("ID: " + id + ", Nome: " + nome + ", Email: " + email);
        }
        rs.close();
    }

    static void listarProfessores(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM professor";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            System.out.println("ID: " + id + ", Nome: " + nome + ", Email: " + email);
        }
        rs.close();
    }

    static void listarProfessorPorRamal(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT r.nome AS ramal_nome, p.nome AS professor_nome FROM ramal r " +
                     "JOIN professor p ON p.id_ramal = r.id " +
                     "GROUP BY r.id, p.id"; 
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String Nomeprofessor = rs.getString("professor_nome");
            String RamalNome= rs.getString("ramal_nome");
            System.out.println("Nome prof: " + Nomeprofessor + " e seu Ramal é " + RamalNome);
        }
        rs.close();
    }

    static void consultarAlunosMatriculados(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT aluno.nome AS aluno_nome, curso.titulo AS curso_titulo FROM aluno " +
                     "JOIN aluno_curso ON aluno.id = aluno_curso.aluno_id " +
                     "JOIN curso ON aluno_curso.curso_id = curso.id";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String nomeAluno = rs.getString("aluno_nome"); 
            String tituloCurso = rs.getString("curso_titulo");
            System.out.println("Aluno: " + nomeAluno + ", Curso: " + tituloCurso);
        }
        rs.close();
    }

    static void mostrarQuantidadeAulas(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT curso.titulo, COUNT(aula.id) AS quantidade_aulas FROM curso " +
                     "JOIN aula ON curso.id = aula.curso_id " +
                     "GROUP BY curso.titulo";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String tituloCurso = rs.getString("titulo");
            int quantidadeAulas = rs.getInt("quantidade_aulas");
            System.out.println("Curso: " + tituloCurso + ", Quantidade de Aulas: " + quantidadeAulas);
        }
        rs.close();
    }

    static void listarRamais(Connection conn) throws SQLException {
        String sql = "SELECT nome FROM ramal";
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String nomeRamal = rs.getString("nome");
                System.out.println("Nome do Ramal: " + nomeRamal);
            }
        }
    }

    static void listarCursosEProfessores(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "SELECT curso.titulo, professor.nome FROM curso " +
                     "JOIN professor ON curso.professor_id = professor.id";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String tituloCurso = rs.getString("titulo");
            String nomeProfessor = rs.getString("nome");
            System.out.println("Curso: " + tituloCurso + ", Professor: " + nomeProfessor);
        }
        rs.close();
    }

    static void atualizar(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "UPDATE aluno SET nome='Novo Nome' WHERE id=1";
        int rowsUpdated = stmt.executeUpdate(sql);
        System.out.println("Linhas atualizadas: " + rowsUpdated);
    }

    static void listarAulasAmbienteDeDados(Connection conn) throws SQLException {
        String sql = "SELECT titulo, descricao FROM aula WHERE titulo = 'Ambiente de Dados'";
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                System.out.println("Título: " + titulo + ", Descrição: " + descricao);
            }
        }
    }

    static void listarNomeEQuantidadeCursosPorAluno(Connection conn) throws SQLException {
        String sql = "SELECT aluno.nome, COUNT(aluno_curso.curso_id) AS quantidade_cursos " +
                     "FROM aluno " +
                     "JOIN aluno_curso ON aluno.id = aluno_curso.aluno_id " +
                     "GROUP BY aluno.nome";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nomeAluno = rs.getString("nome");
                int quantidadeCursos = rs.getInt("quantidade_cursos");
                System.out.println("Aluno: " + nomeAluno + ", Quantidade de Cursos: " + quantidadeCursos);
            }
        }
    }

    static void listarNomeECursosMatriculados(Connection conn) throws SQLException {
        String sql = "SELECT aluno.nome AS nome_aluno, curso.titulo AS nome_curso " +
                     "FROM aluno " +
                     "JOIN aluno_curso ON aluno.id = aluno_curso.aluno_id " +
                     "JOIN curso ON aluno_curso.curso_id = curso.id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nomeAluno = rs.getString("nome_aluno");
                String nomeCurso = rs.getString("nome_curso");
                System.out.println("Aluno: " + nomeAluno + ", Curso: " + nomeCurso);
            }
        }
    }

    static void deletar(Connection conn) throws SQLException {
        String deleteAlunoCursosSql = "DELETE FROM aluno_curso WHERE aluno_id = 2";
        try (PreparedStatement stmtAlunoCursos = conn.prepareStatement(deleteAlunoCursosSql)) {
            int rowsDeleted = stmtAlunoCursos.executeUpdate();
            System.out.println("Registros na tabela aluno_cursos excluídos para aluno com id 2: " + rowsDeleted);
        }
        String deleteAlunoSql = "DELETE FROM aluno WHERE id = 2";
        try (PreparedStatement stmtAluno = conn.prepareStatement(deleteAlunoSql)) {
            int rowsDeleted = stmtAluno.executeUpdate();
            System.out.println("Linhas deletadas na tabela aluno para aluno com id 2: " + rowsDeleted);
        }
    }

    static void inserirComTransacao(Connection conn) throws SQLException {
        long startTime = System.currentTimeMillis();
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        for (int i = 0; i < 10000; i++) {
            String insertSql = "INSERT INTO aluno (nome, email, senha, linkImg) VALUES ('Nome " + i + "', 'email" + i + "@examplo.com', 'senha', 'imagem')";
            stmt.executeUpdate(insertSql);
        }
        conn.commit();
        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de inserção com transação: " + ((endTime - startTime) / 1000.0) +" segundos");
    }
    
    static void inserirSemTransacao(Connection conn) throws SQLException {
        long startTime = System.currentTimeMillis();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < 10000; i++) {
            String insertSql = "INSERT INTO aluno (nome, email, senha, linkImg) VALUES ('Nome " + i + "', 'email" + i + "@examplo.com', 'senha', 'imagem')";
            stmt.executeUpdate(insertSql);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de inserção sem transação: " + ((endTime - startTime) / 1000.0) +" segundos");
    }
    
}
