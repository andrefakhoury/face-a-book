package GUI;

import banco_dados.ConexaoBanco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import content.*;

public class LoginGUI extends JFrame implements ActionListener {
    private JTextField txtUsername, txtPassword;
    private JPanel panMain;
    private JButton btnConnect;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnConnect)) {

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();

            Usuario usuario = conexaoBanco.getUsuario(txtUsername.getText(), txtPassword.getText());

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Nenhum usuario encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                this.setVisible(false);
                UserGUI userGUI = new UserGUI(usuario);
            }
            conexaoBanco.disconnect();

        }
    }

    public LoginGUI() {
        super("Login");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panMain = new JPanel(null);
        txtUsername = new JTextField();
        txtUsername.setBounds(10, 10, 100, 20);
        txtPassword = new JTextField();
        txtPassword.setBounds(10, 40, 100, 20);
        btnConnect = new JButton("Conectar");
        btnConnect.setBounds(10, 70, 100, 20);
        btnConnect.addActionListener(this);

        panMain.add(txtUsername);
        panMain.add(txtPassword);
        panMain.add(btnConnect);

        this.add(panMain);
        this.setVisible(true);
    }
}
