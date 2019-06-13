package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import banco_dados.ConexaoBanco;
import content.*;

public class AdminGUI extends JFrame implements ActionListener {
    // Geral
    private JPanel panMain, panUsuario, panLivro, panCategoria, panEmprestar;
    private JButton btnAddUsuario, btnAddLivro, btnAddCategoria, btnAddEmprestimo;

    // Usuario
    private JButton btnUserAdd, btnUserClear, btnUserCancel;
    private JTextField txtUserNome, txtUserUsername, txtUserPassword, txtUserFoto;
    private JCheckBox ckbUserAdmin;

    private void userClear() {
        txtUserNome.setText("");
        txtUserUsername.setText("");
        txtUserPassword.setText("");
        txtUserFoto.setText("");
        ckbUserAdmin.setSelected(false);
    }

    private void userAdd() {
        String nome = txtUserNome.getText();
        String user = txtUserUsername.getText();
        String pass = txtUserPassword.getText();
        String foto = txtUserFoto.getText();
        boolean adm = ckbUserAdmin.isSelected();

        if (nome.equals("") || user.equals("") || pass.equals("")) {
            JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setAdmin(adm);
            usuario.setUsername(user);
            usuario.setFoto(foto);
            usuario.setPassword(pass);

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();
            boolean added = conexaoBanco.insertUsuario(usuario);
            conexaoBanco.disconnect();

            if (added) {
                userClear();
                this.remove(panUsuario);
                panMain.setVisible(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnAddUsuario)) {
            panMain.setVisible(false);
            panUsuario.setVisible(true);
        } else if (actionEvent.getSource().equals(btnAddLivro)) {
            panMain.setVisible(false);
            this.add(panLivro);
        } else if (actionEvent.getSource().equals(btnAddCategoria)) {
            panMain.setVisible(false);
            this.add(panCategoria);
        } else if (actionEvent.getSource().equals(btnAddEmprestimo)) {
            panMain.setVisible(false);
            this.add(panEmprestar);
        } else if (actionEvent.getSource().equals(btnUserAdd)) {
            userAdd();
        } else if (actionEvent.getSource().equals(btnUserClear)) {
            userClear();
        } else if (actionEvent.getSource().equals(btnUserCancel)) {
            userClear();
            this.remove(panUsuario);
            panMain.setVisible(true);
        }
    }

    public AdminGUI() {
        super("Pagina do admin");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        panMain = new JPanel(null);

        btnAddUsuario = new JButton("Adicionar usuario");
        btnAddUsuario.setBounds(10, 10, 200, 50);
        btnAddUsuario.addActionListener(this);
        panMain.add(btnAddUsuario);

        btnAddLivro = new JButton("Adicionar livro");
        btnAddLivro.setBounds(10, 70, 200, 50);
        btnAddLivro.addActionListener(this);
        panMain.add(btnAddLivro);

        btnAddEmprestimo = new JButton("Adicionar emprestimo");
        btnAddEmprestimo.setBounds(10, 130, 200, 50);
        btnAddEmprestimo.addActionListener(this);
        panMain.add(btnAddEmprestimo);

        btnAddCategoria = new JButton("Adicionar categoria");
        btnAddCategoria.setBounds(10, 190, 200, 50);
        btnAddCategoria.addActionListener(this);
        panMain.add(btnAddCategoria);

        this.add(panMain);
        this.setVisible(true);

        panUsuario = new JPanel(new GridLayout(0, 1));
        panUsuario.setVisible(false);

        txtUserNome = new JTextField("NOME");
        panUsuario.add(txtUserNome);

        txtUserUsername = new JTextField("USERNAME");
        panUsuario.add(txtUserUsername);

        txtUserPassword = new JTextField("SENHA");
        panUsuario.add(txtUserPassword);

        txtUserFoto = new JTextField("FOTO");
        panUsuario.add(txtUserFoto);

        ckbUserAdmin = new JCheckBox("Admin");
        panUsuario.add(ckbUserAdmin);

        btnUserAdd = new JButton("Adicionar");
        btnUserAdd.addActionListener(this);
        panUsuario.add(btnUserAdd);

        btnUserClear = new JButton("Limpar");
        btnUserClear.addActionListener(this);
        panUsuario.add(btnUserClear);

        btnUserCancel = new JButton("Cancelar");
        btnUserCancel.addActionListener(this);
        panUsuario.add(btnUserCancel);

        this.add(panUsuario);

        panEmprestar = new JPanel(null);
        panEmprestar.setVisible(false);

        panLivro = new JPanel(null);
        panLivro.setVisible(false);

        panCategoria = new JPanel(null);
        panCategoria.setVisible(false);

        this.add(panEmprestar);
        this.add(panLivro);
        this.add(panCategoria);

    }


}
