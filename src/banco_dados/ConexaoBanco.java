package banco_dados;

import javax.swing.*;
import javax.xml.catalog.Catalog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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

        String fsql = "SELECT * FROM livro LEFT JOIN categoria ON livro.id_categoria = categoria.id_categoria ORDER BY livro.id_livro";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("categoria.id_categoria"), rs.getString("categoria.nome"));
                Livro livro = new Livro(rs.getInt("livro.id_livro"), categoria, rs.getString("livro.nome"), rs.getString("livro.autor"), rs.getString("livro.foto"));
                livros.add(livro);
            }
            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção dos livros: "+ ex);
        }

        return livros;
    }

    public Usuario getUsuario(String username, String password) {
        Usuario usuario = null;

        String fsql = "SELECT * FROM usuario WHERE username=?";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if(rs.next() && password != null && password.equals(rs.getString("password"))) {
                usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nome"));
                usuario.setFoto(rs.getString("foto"));
                usuario.setUsername(rs.getString("username"));
                usuario.setAdmin(rs.getBoolean("admin"));
            }

            pstmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção do usuario: "+ ex);
        }

        return usuario;
    }

    public boolean insertUsuario(Usuario usuario) {
        if (Objects.isNull(usuario)) {
            return false;
        }

        String fsql = "INSERT INTO usuario (username, password, admin, nome, foto) VALUES (?, ?, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(fsql);
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setBoolean(3, usuario.isAdmin());
            pstmt.setString(4, usuario.getNome());
            pstmt.setString(5, usuario.getFoto());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o usuario: " + ex);
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao resetar o banco: "+ ex);
        }
    }
}
