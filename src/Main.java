import GUI.AdminGUI;
import GUI.LoginGUI;
import GUI.UserGUI;
import banco_dados.ConexaoBanco;
import content.Usuario;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        ConexaoBanco conexaoBanco = new ConexaoBanco();
//        conexaoBanco.connect();
//
//        conexaoBanco.test();
//
//        LoginGUI loginGUI = new LoginGUI();
//
//        conexaoBanco.disconnect();

//        ConexaoBanco conexaoBanco = new ConexaoBanco();
//        conexaoBanco.connect();
//
////        conexaoBanco.resetDB();
//
//        Usuario usuario = conexaoBanco.getUsuario("admin", "admin");
//
//        if (usuario == null) {
//            JOptionPane.showMessageDialog(null, "Nenhum usuario encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
//        } else {
//            UserGUI userGUI = new UserGUI(usuario);
//        }
//        conexaoBanco.disconnect();

        AdminGUI adminGUI = new AdminGUI();
    }
}
