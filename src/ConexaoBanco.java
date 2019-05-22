import javax.swing.*;
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
        usuario = "postgres";
        senha = "sqladmin";
        drive = "org.postgresql.Driver";
        url = "jdbc:postgresql://localhost:5432/jogo_castrin";
    }

    public Livro getLivro() {
        return new Livro(1, "aaa", new Categoria(1, "abacaba"));
    }

    public Usuario getUsuario(String username, String senha) {
        return new Usuario();
    }
}
