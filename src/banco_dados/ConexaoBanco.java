package banco_dados;

import javax.swing.*;
import javax.swing.text.html.StyleSheet;
import javax.xml.catalog.Catalog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.security.*;
import java.math.*;

import content.*;

public class ConexaoBanco {
    private String url, usuario, senha, drive;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private Statement stmt;

    public void connect() {
        try {
            Class.forName(drive);
            con = DriverManager.getConnection(url, usuario, senha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na conexão: "+ ex);
        }
    }

    public void disconnect() {
        try {
            con.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na desconexão: "+ ex);
        }
    }

    public ConexaoBanco() {
        con = null;
        usuario = "fakhoury";
        senha = "fakhoury";
        drive = "org.postgresql.Driver";
        url = "jdbc:postgresql://localhost:5432/face_a_book";
    }

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

    public boolean updateCategoria(Categoria categoria) {
        if (Objects.isNull(categoria)) {
            return false;
        }

        String fsql = "UPDATE categoria SET nome = ? WHERE id_categoria = ?;";

        try {
            pstmt = con.prepareStatement(fsql);

            System.out.println(categoria);

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

    public void resetDB() {
        String sql = "";

        File file = new File("./etc/reset.sql");
        Scanner scanner;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
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
