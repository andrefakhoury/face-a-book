import javax.swing.*;
import javax.xml.catalog.Catalog;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
