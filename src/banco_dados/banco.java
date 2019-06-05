package banco_dados;
import java.sql.*;
import java.util.*; //ArrayList
import javax.swing.*;

public class banco {
    private String fsql;
    private String url, usuario, senha, drive;
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pstmt;
    private Statement stmt;

    private String bdProdCod, bdProdNome, bdProdAno, bdProdFone;
    private String bdJogoCod, bdJogoCodProd, bdJogoNome, bdJogoPreco, bdJogoFoto;

    public banco() {
        bdProdCod = ""; bdProdNome = ""; bdProdAno = ""; bdProdFone = "";
        bdJogoCod = ""; bdJogoCodProd = ""; bdJogoNome = ""; bdJogoPreco = ""; bdJogoFoto = "";
        fsql = "";
        con = null;
        usuario = "fakhoury";
        senha = "fakhoury";
        drive = "org.postgresql.Driver";
        url = "jdbc:postgresql://localhost:5432/face_a_book";
    }

    public void setProdCod(String x) { this.bdProdCod = x; }
    public void setProdNome(String x) { this.bdProdNome = x; }
    public void setProdAno(String x) { this.bdProdAno = x; }
    public void setProdFone(String x) { this.bdProdFone = x; }
    public void setJogoCod(String x) { this.bdJogoCod = x; }
    public void setJogoCodProd(String x) { this.bdJogoCodProd = x; }
    public void setJogoNome(String x) { this.bdJogoNome = x; }
    public void setJogoPreco(String x) { this.bdJogoPreco = x; }
    public void setJogoFoto(String x) { this.bdJogoFoto = x; }

    public String getProdCod() { return this.bdProdCod; }
    public String getProdNome() { return this.bdProdNome; }
    public String getProdAno() { return this.bdProdAno; }
    public String getProdFone() { return this.bdProdFone; }
    public String getJogoCod() { return this.bdJogoCod; }
    public String getJogoCodProd() { return this.bdJogoCodProd; }
    public String getJogoNome() { return this.bdJogoNome; }
    public String getJogoPreco() { return this.bdJogoPreco; }
    public String getJogoFoto() { return this.bdJogoFoto; }

    public void incluirJogo(){
        fsql = "insert into jogo (cod_produtora, nome, preco, foto) values (?,?,?,?)";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setInt(1, Integer.parseInt(bdJogoCodProd));
            pstmt.setString(2, bdJogoNome);
            pstmt.setFloat(3, Float.parseFloat(bdJogoPreco));
            pstmt.setString(4, bdJogoFoto);
            pstmt.execute();
            pstmt.close();

        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na inclusão do jogo: "+ ex); }
    }

    public void incluirProd(){
        fsql = "insert into produtora (nome, ano_surgimento, telefone) values (?,?,?)";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setString(1, bdProdNome);
            pstmt.setString(2, bdProdAno);
            pstmt.setString(3, bdProdFone);
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na inclusão da produtora: "+ ex); }
    }

    public void excluirProd() {
        fsql = "delete from produtora where codigo = ?";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setInt(1, Integer.parseInt(bdProdCod));
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na exclusão da produtora: "+ ex); }
    }

    public void excluirJogo() {
        fsql = "delete from jogo where codigo = ?";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setInt(1, Integer.parseInt(bdJogoCod));
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na exclusão do jogo: "+ ex); }
    }

    public void alterarJogo() {
        fsql = "update jogo set cod_produtora = ?, nome = ?, foto = ?, preco = ? where codigo = ?";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setInt(1, Integer.parseInt(bdJogoCodProd));
            pstmt.setString(2, bdJogoNome);
            pstmt.setString(3, bdJogoFoto);
            pstmt.setFloat(4, Float.parseFloat(bdJogoPreco));
            pstmt.setInt(5, Integer.parseInt(bdJogoCod));

            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na alteração do jogo: "+ ex); }
    }

    public void alterarProd() {
        fsql = "update produtora set nome = ?, telefone = ?, ano_surgimento = ? where codigo = ?";
        try {
            pstmt = con.prepareStatement (fsql);
            pstmt.setString(1, bdProdNome);
            pstmt.setString(2, bdProdFone);
            pstmt.setString(3, bdProdAno);
            pstmt.setInt(4, Integer.parseInt(bdProdCod));
            pstmt.execute();
            pstmt.close();
        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro na alteração: "+ ex); }
    }

    public void connect () {
        try {
            Class.forName(drive);
            con = DriverManager.getConnection(url, usuario, senha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na conexão: "+ ex);
        }
    }

    public void disconnect () {
        try {
            con.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na desconexão: "+ ex);
        }
    }

    public ArrayList pegaDadosJogo() {
        fsql = "select jogo.*, produtora.nome as nome_produtora from jogo left join produtora on jogo.cod_produtora = produtora.codigo order by jogo.codigo";
        ArrayList dados = new ArrayList();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);
            while (rs.next()) {
                bdJogoCod = rs.getString("codigo");
                bdJogoNome = rs.getString("nome");
                bdJogoCodProd = rs.getString("cod_produtora");
                bdProdNome = rs.getString("nome_produtora");
                bdJogoPreco = rs.getString("preco");
                bdJogoFoto = rs.getString("foto");

                dados.add(bdJogoCod);
                dados.add(bdJogoNome);
                dados.add(bdJogoCodProd);
                dados.add(bdProdNome);
                dados.add(bdJogoPreco);
                dados.add(bdJogoFoto);
            }
            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção do jogo: "+ ex);
        }
        return dados;
    }

    public ArrayList pegaDadosProd() {
        fsql = "select * from produtora order by codigo";
        ArrayList dados = new ArrayList();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(fsql);
            while (rs.next()) {

                bdProdCod = rs.getString("codigo");
                bdProdNome = rs.getString("nome");
                bdProdAno = rs.getString("ano_surgimento");
                bdProdFone = rs.getString("telefone");
                dados.add(bdProdCod);
                dados.add(bdProdNome);
                dados.add(bdProdAno);
                dados.add(bdProdFone);
            }
            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na seleção: "+ ex);
        }
        return dados;
    }

    public boolean procuraJogo(String xx)
    {
        String sql="Select * from jogo where codigo=? order by codigo";
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, xx);
            rs=pstmt.executeQuery();
            if(rs.next())
            {
                return true;
            }
            pstmt.close();
        }    catch(Exception erroi)
        {
            JOptionPane.showMessageDialog(null,
                    " Erro procura:"+erroi);
        }
        return false;
    }//procura jogo

    public boolean procuraProd(String xx)
    {
        String sql="Select * from produtora where codigo=? order by codigo";
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, xx);
            rs=pstmt.executeQuery();
            if(rs.next())
            {
                return true;
            }
            pstmt.close();
        }    catch(Exception erroi)
        {
            JOptionPane.showMessageDialog(null,
                    " Erro procura:"+erroi);
        }
        return false;
    }//procura produtora

    public String procuraCodProd(String xx)
    {
        String sql="Select codigo from produtora where nome=? order by codigo";
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, xx);
            rs=pstmt.executeQuery();
            if(rs.next())
            {
                return rs.getString("codigo") + "";
            }
            pstmt.close();
        }    catch(Exception erroi)
        {
            JOptionPane.showMessageDialog(null,
                    " Erro procura:"+erroi);
        }
        return "0";
    }//procura produtora

    public String retornaJogo()
    {
        String volta="0";
        String sql = "SELECT last_value FROM jogo_codigo_seq";
        try{
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery(sql);

            if(rs.last()) { volta=rs.getString("last_value"); }
            else { volta = "0"; }

        } catch(Exception erroi) {
            JOptionPane.showMessageDialog(null, "Erro leitura :" + erroi);
        }

        return volta;
    }

    public String retornaProd()
    {
        String volta="0";
        String sql = "SELECT last_value FROM produtora_codigo_seq";
        try{
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery(sql);

            if(rs.last()) { volta=rs.getString("last_value"); }
            else { volta = "0"; }

        } catch(Exception erroi) {
            JOptionPane.showMessageDialog(null, "Erro leitura :" + erroi);
        }

        return volta;
    }
}