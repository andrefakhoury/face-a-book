package banco_dados;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import content.*;

/**
 * Classe que faz a conecção com o banco de dados
 */
public class ConexaoBanco {
    private String url, usuario, senha, drive;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private Statement stmt;

    /**
     * Cria uma conecção com o banco de dados
     */
    public void connect() {
        try {
            Class.forName(drive);
            con = DriverManager.getConnection(url, usuario, senha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na conexão: "+ ex);
        }
    }
    /**
     * Desconecta do banco de dados
     */
    public void disconnect() {
        try {
            con.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na desconexão: "+ ex);
        }
    }
    
    /**
     * Cria uma instancia do banco de dados e conecta com as informaçoes do arquivo de texto DataInfo.txt
     */
    public ConexaoBanco() {
        con = null;
        drive = "org.postgresql.Driver";

        File input = new File(Paths.get("").toAbsolutePath().toString() + "/DataInfo.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(input);

            usuario = scanner.nextLine();
            senha = scanner.nextLine();
            url = scanner.nextLine();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco.\n" +
                    "Verifique se o arquivo DataInfo.txt foi criado.");
            System.exit(1);
        }
    }
    
    /**
     * Verifica se a conecção esta estabelecida
     * @return se a conecção esta estabelecida
     */
    public boolean testConnection() {
        boolean test = true;

        try {
            Class.forName(drive);
            con = DriverManager.getConnection(url, usuario, senha);
            con.close();
        } catch (Exception ex) {
            test = false;
        } finally {
            con = null;
        }

        return test;
    }
    
    /**
     * Classe para testar uma inserção de categpria no banco de dados
     */
    public void test() {
        String fsql;

        fsql = "insert into categoria (nome) values (?)";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setString(1, "Gibi");
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na inclusão da categoria: "+ ex); }

        fsql = "insert into categoria (nome) values (?)";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setString(1, "Aventura");
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na inclusão da categoria: "+ ex); }
    }
    
    /**
     * Função que pega string como parametro e faz md5 dela
     * @param md5 String a ser criptografada
     * @return String criptografada com md5
     */
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * Retorna categorias existentes no banco de dados
     * @return ArrayList<Categoria> com categorias existentes no banco de dados
     */
    public ArrayList<Categoria> getCategorias() {
        ArrayList<Categoria> categorias = new ArrayList<>();

        String fsql = "SELECT * FROM categoria ORDER BY categoria.id_categoria";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);
            while (rs.next()) {
                categorias.add(new Categoria(rs.getInt("id_categoria"), rs.getString("nome")));
            }
            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção das categorias: "+ ex);
        }

        return categorias;
    }
    
    /**
     * Retorna livros existentes no banco de dados
     * @return ArrayList<Livro> com livros existentes no banco de dados
     */
    public ArrayList<Livro> getLivros() {
        ArrayList<Livro> livros = new ArrayList<>();

        String fsql = "SELECT * FROM livro INNER JOIN categoria ON livro.id_categoria = categoria.id_categoria ORDER BY livro.id_livro";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("id_categoria"), rs.getString("nome"));
                Livro livro = new Livro(rs.getInt("id_livro"), categoria, rs.getString("nome"), rs.getString("autor"), rs.getString("foto"));
                livros.add(livro);
            }
            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção dos livros: "+ ex);
        }

        return livros;
    }
    
    /**
     * Busca por livros a partir de uma string com os criterios da busca
     * @param criterios com criterios de busca dos livros
     * @return ArrayList<Livro> com livros achados na busca
     */
    public ArrayList<Livro> getLivros(String criterios) {
        ArrayList<Livro> livros = new ArrayList<>();

        String fsql = "SELECT * FROM livro INNER JOIN categoria ON livro.id_categoria = categoria.id_categoria " +
                "WHERE lower(livro.nome) LIKE ? OR lower(livro.autor) LIKE ? OR lower(categoria.nome) LIKE ? ORDER BY livro.id_livro";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setString(1, '%' + criterios.toLowerCase() + '%');
            pstmt.setString(2, '%' + criterios.toLowerCase() + '%');
            pstmt.setString(3, '%' + criterios.toLowerCase() + '%');
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("id_categoria"), rs.getString("nome"));
                Livro livro = new Livro(rs.getInt("id_livro"), categoria, rs.getString("nome"), rs.getString("autor"), rs.getString("foto"));
                livros.add(livro);
            }
            pstmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção dos livros: "+ ex);
        }

        return livros;
    }
    
    /**
     * Verifica os emprestimos de um usuario
     * @param usuario - objeto do usuario que deseja pegar os livros
     * @return ArrayList<Emprestimo> com os emprestimos de um usuario
     */
    public ArrayList<Emprestimo> getEmprestimos (Usuario usuario) {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        String fsql = "SELECT emp.id_emprestimo AS id, emp.data_entrega AS data, emp.status_emprestimo AS status,\n" +
                "\tlivro.id_livro AS id_livro, livro.nome AS nome_livro, livro.autor AS autor_livro, livro.foto AS foto_livro,\n" +
                "\tcat.id_categoria as id_cat, cat.nome as nome_cat\n" +
                "FROM emprestimo AS emp\n" +
                "INNER JOIN disponibilidade AS disp ON emp.id_disponibilidade = disp.id_disponibilidade\n" +
                "INNER JOIN livro ON livro.id_livro = disp.id_livro\n" +
                "INNER JOIN categoria AS cat ON livro.id_categoria = cat.id_categoria\n" +
                "WHERE emp.id_usuario = ? AND emp.status_emprestimo <> 2\n" +
                "ORDER BY data_entrega ASC; ";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, usuario.getId());
            rs = pstmt.executeQuery();

            while(rs.next()) {
                Emprestimo emprestimo = new Emprestimo(rs.getInt("id"));
                emprestimo.setStatus(rs.getInt("status"));
                emprestimo.setDataEntrega(rs.getDate("data"));

                Categoria categoria = new Categoria(rs.getInt("id_cat"), rs.getString("nome_cat"));
                Livro livro = new Livro(rs.getInt("id_livro"), categoria, rs.getString("nome_livro"), rs.getString("autor_livro"), rs.getString("foto_livro"));
                emprestimo.setLivro(livro);

                emprestimos.add(emprestimo);
            }

            pstmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção dos emprestimos: "+ ex);
        }

        return emprestimos;
    }
    
    /**
     * Verifica todos os emprestimos da biblioteca
     * @return ArrayList<Emprestimo> com todos os emprestimos da biblioteca
     */
    public ArrayList<Emprestimo> getEmprestimos () {

        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        String fsql = "SELECT emp.id_emprestimo AS id, emp.data_entrega AS data, emp.status_emprestimo AS status,\n" +
                "\tlivro.id_livro AS id_livro, livro.nome AS nome_livro, livro.autor AS autor_livro, livro.foto AS foto_livro,\n" +
                "\tcat.id_categoria as id_cat, cat.nome as nome_cat\n" +
                "FROM emprestimo AS emp\n" +
                "INNER JOIN disponibilidade AS disp ON emp.id_disponibilidade = disp.id_disponibilidade\n" +
                "INNER JOIN livro ON livro.id_livro = disp.id_livro\n" +
                "INNER JOIN categoria AS cat ON livro.id_categoria = cat.id_categoria\n" +
                "WHERE emp.status_emprestimo <> 2\n" +
                "ORDER BY data_entrega ASC; ";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);

            while(rs.next()) {
                Emprestimo emprestimo = new Emprestimo(rs.getInt("id"));
                emprestimo.setStatus(rs.getInt("status"));
                emprestimo.setDataEntrega(rs.getDate("data"));

                Categoria categoria = new Categoria(rs.getInt("id_cat"), rs.getString("nome_cat"));
                Livro livro = new Livro(rs.getInt("id_livro"), categoria, rs.getString("nome_livro"), rs.getString("autor_livro"), rs.getString("foto_livro"));
                emprestimo.setLivro(livro);

                emprestimos.add(emprestimo);
            }

            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção dos emprestimos: "+ ex);
        }

        return emprestimos;
    }

    /**
     * Verifica as disponibilidades de um usuario
     * @param usuario - objeto do usuario que deseja buscar pelas disponibilidades
     * @return ArrayList<Disponibilidade> com as disponibilidades do usuario
     */
    public ArrayList<Disponibilidade> getDisponibilidades (Usuario usuario) {
        ArrayList<Disponibilidade> disponibilidades = new ArrayList<>();

        String fsql = "SELECT disp.id_disponibilidade AS id, emp.id_emprestimo AS emp_id, disp.data_limite as data,\n" +
                "\tlivro.id_livro AS id_livro, livro.nome AS nome_livro, livro.autor AS autor_livro, livro.foto AS foto_livro,\n" +
                "\tcat.id_categoria as id_cat, cat.nome as nome_cat,\n" +
                "CASE\n" +
                "\tWHEN emp.id_emprestimo IS NULL THEN 0\n" +
                "\tWHEN emp.status_emprestimo = 2 THEN 0\n" +
                "\tELSE 1\n" +
                "END AS status\n" +
                "FROM disponibilidade AS disp\n" +
                "LEFT JOIN emprestimo AS emp ON emp.id_disponibilidade = disp.id_disponibilidade\n" +
                "LEFT JOIN livro ON livro.id_livro = disp.id_livro\n" +
                "LEFT JOIN categoria AS cat ON livro.id_categoria = cat.id_categoria\n" +
                "WHERE disp.id_usuario = ?\n" +
                "ORDER BY disp.data_limite ASC;";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, usuario.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Disponibilidade disponibilidade = new Disponibilidade(rs.getInt("id"));
                disponibilidade.setData(rs.getDate("data"));
                disponibilidade.setStatus(rs.getInt("status"));

                Categoria categoria = new Categoria(rs.getInt("id_cat"), rs.getString("nome_cat"));
                Livro livro = new Livro(rs.getInt("id_livro"), categoria, rs.getString("nome_livro"), rs.getString("autor_livro"), rs.getString("foto_livro"));
                disponibilidade.setLivro(livro);

                disponibilidades.add(disponibilidade);
            }

            pstmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção das disponibilidades: "+ ex);
        }

        return disponibilidades;
    }
    
    /**
     * Função para retornar todos os usuarios cadastrados no sistema
     * @return ArrayList<Usuario> com todos os usuarios do sistema
     */
    public ArrayList<Usuario> getUsuarios () {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        String fsql = "SELECT * FROM usuario ORDER BY id_usuario";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
//                usuario.setPassword(rs.getString("password"));
                usuario.setNome(rs.getString("nome"));
                usuario.setFoto(rs.getString("foto"));
                usuario.setAdmin(rs.getBoolean("admin"));

                usuarios.add(usuario);
            }

            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção dos usuarios: "+ ex);
        }

        return usuarios;
    }

    /**
     * Busca usuario no banco de dados
     * @param username String com nome do usuario
     * @param password - String com senha do usuario
     * @return Objeto Usuario com informaçoes do usuario
     */
    public Usuario getUsuario(String username, String password) {
        Usuario usuario = null;

        String fsql = "SELECT * FROM usuario WHERE username=?";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (password == null || MD5(password).equals(rs.getString("password"))) {
                    usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nome"));
                    usuario.setFoto(rs.getString("foto"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setAdmin(rs.getBoolean("admin"));
                }
            }

            pstmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção do usuario: "+ ex);
        }

        return usuario;
    }

    /**
     * Busca por disponibilidade de um livro
     * @param livro Objeto Livro que deseja buscar por disponibilidades
     * @return ArrayList<Disponibilidade> com as disponibilidades de um livro
     */
    public ArrayList<Disponibilidade> getDisponibilidades(Livro livro) {
        ArrayList<Disponibilidade> disponibilidades = new ArrayList<>();

        String fsql = "SELECT disp.id_disponibilidade AS id, emp.id_emprestimo AS emp_id, disp.data_limite as data,\n" +
                "CASE\n" +
                "\tWHEN emp.id_emprestimo IS NULL THEN 0\n" +
                "\tWHEN emp.status_emprestimo = 2 THEN 0\n" +
                "\tELSE 1\n" +
                "END AS status\n" +
                "FROM disponibilidade AS disp\n" +
                "LEFT JOIN emprestimo AS emp ON emp.id_disponibilidade = disp.id_disponibilidade\n" +
                "LEFT JOIN livro ON livro.id_livro = disp.id_livro\n" +
                "LEFT JOIN categoria AS cat ON livro.id_categoria = cat.id_categoria\n" +
                "WHERE disp.id_livro = ?\n" +
                "ORDER BY disp.data_limite DESC;";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, livro.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Disponibilidade disponibilidade = new Disponibilidade(rs.getInt("id"));
                disponibilidade.setData(rs.getDate("data"));
                disponibilidade.setStatus(rs.getInt("status"));

                disponibilidade.setLivro(livro);

                if (disponibilidade.getStatus() == 0) {
                    disponibilidades.add(disponibilidade);
                }
            }

            pstmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção das disponibilidades: "+ ex);
        }

        return disponibilidades;
    }

    /**
     * Função que retorna todas as disponibilidades 
     * @return ArrayList<Disponibilidade> com todas as disponibilidades no banco de dados
     */
    public ArrayList<Disponibilidade> getDisponibilidades() {
        ArrayList<Disponibilidade> disponibilidades = new ArrayList<>();

        String fsql = "SELECT disp.id_disponibilidade AS id, emp.id_emprestimo AS emp_id, disp.data_limite as data,\n" +
                "\tlivro.id_livro AS id_livro, livro.nome AS nome_livro, livro.autor AS autor_livro, livro.foto AS foto_livro,\n" +
                "\tcat.id_categoria as id_cat, cat.nome as nome_cat,\n" +
                "CASE\n" +
                "\tWHEN emp.id_emprestimo IS NULL THEN 0\n" +
                "\tWHEN emp.status_emprestimo = 2 THEN 0\n" +
                "\tELSE 1\n" +
                "END AS status\n" +
                "FROM disponibilidade AS disp\n" +
                "LEFT JOIN emprestimo AS emp ON emp.id_disponibilidade = disp.id_disponibilidade\n" +
                "LEFT JOIN livro ON livro.id_livro = disp.id_livro\n" +
                "LEFT JOIN categoria AS cat ON livro.id_categoria = cat.id_categoria\n" +
                "ORDER BY disp.data_limite ASC;";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);
            while (rs.next()) {
                Disponibilidade disponibilidade = new Disponibilidade(rs.getInt("id"));
                disponibilidade.setData(rs.getDate("data"));
                disponibilidade.setStatus(rs.getInt("status"));

                Categoria categoria = new Categoria(rs.getInt("id_cat"), rs.getString("nome_cat"));
                Livro livro = new Livro(rs.getInt("id_livro"), categoria, rs.getString("nome_livro"), rs.getString("autor_livro"), rs.getString("foto_livro"));
                disponibilidade.setLivro(livro);

                if (disponibilidade.getStatus() == 0) {
                    disponibilidades.add(disponibilidade);
                }
            }

            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção das disponibilidades: "+ ex);
        }

        return disponibilidades;
    }
    
    /**
     * Função para remover uma disponibilidade 
     * @param disponibilidade Objeto Disponiblidade
     * @return Se conseguiu remover a disponibilidade ou nao
     */
    public boolean removerDisponibilidade(Disponibilidade disponibilidade) {
        if (Objects.isNull(disponibilidade)) {
            return false;
        }

        String fsql = "DELETE FROM emprestimo WHERE id_disponibilidade = ?;";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, disponibilidade.getId());
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover emprestimos: " + ex);
            return false;
        }

        fsql = "DELETE FROM disponibilidade WHERE id_disponibilidade = ?;";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, disponibilidade.getId());
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover disponibilidades: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função que empresta um livro a um usuario
     * @param disponibilidade Objeto Disponibilidade do livro
     * @param usuario Objeto Usuario que deseja pergar um emprestimo
     * @param dataEntrega Date com data final de devolução
     * @return se o empretimo foi efetuado
     */
    public boolean emprestarLivro(Disponibilidade disponibilidade, Usuario usuario, Date dataEntrega) {
        if (Objects.isNull(disponibilidade) || Objects.isNull(usuario)) {
            return false;
        }

        String fsql = "INSERT INTO emprestimo (id_disponibilidade, id_usuario, status_emprestimo, data_entrega) VALUES (?, ?, 0, ?);";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, disponibilidade.getId());
            pstmt.setInt(2, usuario.getId());
            pstmt.setDate(3, dataEntrega);
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o emprestimo: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função que devolve um livro emprestado
     * @param emprestimo Objeto Emprestimo que representa um emprestimo
     * @return se conseguiu devolver o livro
     */
    public boolean devolverLivro(Emprestimo emprestimo) {
        if (Objects.isNull(emprestimo)) {
            return false;
        }

        String fsql = "UPDATE emprestimo SET status_emprestimo = 2 WHERE id_emprestimo = ?;";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, emprestimo.getId());
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na devolucao: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Comfirma um Emprestimo
     * @param emprestimo Objeto Emprestimo que deseja comfirmar
     * @return se confirmou o emprestimo
     */
    public boolean confirmarEmprestimo(Emprestimo emprestimo) {
        if (Objects.isNull(emprestimo)) {
            return false;
        }

        String fsql = "UPDATE emprestimo SET status_emprestimo = 1 WHERE id_emprestimo = ?;";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, emprestimo.getId());
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro no emprestimo: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função que atualiza um usuario
     * @param usuario Objeto Usuario com o usuario a ser atualizado com as informaçoes novas
     * @param password String senha do usuario
     * @return se conseguiu atualizar usuario
     */
    public boolean updateUsuario(Usuario usuario, String password) {
        if (Objects.isNull(usuario)) {
            return false;
        }

        String fsql = "";
        if (password == null) {
            fsql = "UPDATE usuario SET foto = ?, admin = ?, nome = ?, username = ? WHERE id_usuario = ?;";
        } else {
            fsql = "UPDATE usuario SET foto = ?, admin = ?, nome = ?, username = ?, password = ? WHERE id_usuario = ?;";
        }

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setString(1, usuario.getFoto());
            pstmt.setBoolean(2, usuario.isAdmin());
            pstmt.setString(3, usuario.getNome());
            pstmt.setString(4, usuario.getUsername());

            if (password != null) {
                pstmt.setString(5, MD5(password));
                pstmt.setInt(6, usuario.getId());
            } else {
                pstmt.setInt(5, usuario.getId());
            }

            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar o usuario: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função para inserir um usuario no banco de dados
     * @param usuario Objeto Usuario com usuario a ser inserido
     * @param password String senha do usuario
     * @return se conseguiu inserir usuario
     */
    public boolean insertUsuario(Usuario usuario, String password) {
        if (Objects.isNull(usuario) || Objects.isNull(password)) {
            return false;
        }

        String fsql = "INSERT INTO usuario (username, password, admin, nome, foto) VALUES (?, ?, ?, ?, ?);";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, MD5(password));
            pstmt.setBoolean(3, usuario.isAdmin());
            pstmt.setString(4, usuario.getNome());
            pstmt.setString(5, usuario.getFoto());
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o usuario: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função que insere uma categoria
     * @param categoria Objeto Categoria com a categoria a ser inserida 
     * @return se conseguiu inserir categoria
     */
    public boolean insertCategoria(Categoria categoria) {
        if (Objects.isNull(categoria)) {
            return false;
        }

        String fsql = "INSERT INTO categoria (nome) VALUES (?);";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setString(1, categoria.getNome());
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar a categoria: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Funçao que atualiza uma categoria
     * @param categoria Objeto Categoria a ser atualizado com as informaçoes novas
     * @return se conseguiu atualizar a categoria
     */
    public boolean updateCategoria(Categoria categoria) {
        if (Objects.isNull(categoria)) {
            return false;
        }

        String fsql = "UPDATE categoria SET nome = ? WHERE id_categoria = ?;";

        try {
            pstmt = con.prepareStatement(fsql);

            pstmt.setString(1, categoria.getNome());
            pstmt.setInt(2, categoria.getId());

            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar a categoria: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função que atualiza um livro
     * @param livro Objeto Livro a ser atualizado com as informaçoes novas
     * @return se livro foi atualizado
     */
    public boolean updateLivro(Livro livro) {
        if (Objects.isNull(livro)) {
            return false;
        }

        String fsql = "UPDATE livro SET nome = ?, autor = ?, foto = ?, id_categoria = ? WHERE id_livro = ?;";

        try {
            pstmt = con.prepareStatement(fsql);

            pstmt.setString(1, livro.getNome());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getFoto());
            pstmt.setInt(4, livro.getCategoria().getId());
            pstmt.setInt(5, livro.getId());

            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o usuario: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função para verificar se existe um emprestimo
     * @param idDisponibilidade chave inteira para buscar Emprestimo 
     * @return se existe ou nao
     */
    public boolean existEmprestimo (int idDisponibilidade) {
        String fsql = "SELECT * FROM emprestimo WHERE id_disponibilidade = ? AND status_emprestimo <> 2";
        boolean exists = false;

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, idDisponibilidade);
            rs = pstmt.executeQuery();

            exists = rs.next();

            pstmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção do emprestimo: "+ ex);
        }

        return exists;
    }

    /**
     * Função para atualizar uma disponibilidade
     * @param idDisponibilidade chave inteira pra buscar disponibilidade
     * @param idLivro chave inteira do livro
     * @param data Date com nova data
     * @return
     */
    public boolean updateDisponibilidade(int idDisponibilidade, int idLivro, Date data) {
        if (Objects.isNull(data)) {
            return false;
        }

        if (existEmprestimo(idDisponibilidade)) {
            return false;
        }

        String fsql = "UPDATE disponibilidade SET id_livro = ?, data_limite = ? WHERE id_disponibilidade = ?;";

        try {
            pstmt = con.prepareStatement(fsql);

            pstmt.setInt(1, idLivro);
            pstmt.setDate(2, data);
            pstmt.setInt(3, idDisponibilidade);

            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar a disponibilidade: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função para inserir um livro
     * @param livro Objeto Livro a ser inserido
     * @return se conseguiu inserir ou nao
     */
    public boolean insertLivro(Livro livro) {
        if (Objects.isNull(livro)) {
            return false;
        }

        String fsql = "INSERT INTO livro (id_categoria, nome, autor, foto) VALUES (?, ?, ?, ?);";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, livro.getCategoria().getId());
            pstmt.setString(2, livro.getNome());
            pstmt.setString(3, livro.getAutor());
            pstmt.setString(4, livro.getFoto());

            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o livro: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função inserir disponibilidade
     * @param idLivro chave inteira do livro
     * @param idUsuario chave inteira do usuario
     * @param data Date com data da disponibilidade
     * @return se conseguiu inserir
     */
    public boolean insertDisponibilidade(int idLivro, int idUsuario, Date data) {
        if (Objects.isNull(data)) {
            return false;
        }

        String fsql = "INSERT INTO disponibilidade (id_livro, id_usuario, data_limite) VALUES (?, ?, ?);";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setInt(1, idLivro);
            pstmt.setInt(2, idUsuario);
            pstmt.setDate(3, data);

            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar a disponibilidade: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Função Que reseta o banco de dados 
     */
    public void resetDB() {
        String sql = "";

        File file = new File("./etc/reset.sql");
        Scanner scanner;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found");
            return;
        }

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            sql += str + "\n";
        }

        scanner.close();

        try {
            stmt = con.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao resetar o banco: "+ ex);
        }
    }
}