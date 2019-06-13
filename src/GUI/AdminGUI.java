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

    // Livro
    private JButton btnLivroAdd, btnLivroClear, btnLivroCancel;
    private JTextField txtLivroNome, txtLivroAutor, txtLivroCatId, txtLivroCatNome, txtLivroFoto;

    // Categoria
    private JButton btnCategoriaAdd, btnCategoriaClear, btnCategoriaCancel;
    private JTextField txtCategoriaNome;

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

    private void livroClear() {
        txtLivroFoto.setText("");
        txtLivroCatNome.setText("");
        txtLivroCatId.setText("");
        txtLivroAutor.setText("");
        txtLivroAutor.setText("");
        txtLivroNome.setText("");
    }

    private void livroAdd() {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnAddUsuario)) {
            panMain.setVisible(false);
            this.add(panUsuario);
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
            panMain.setVisible(true);
            this.remove(panUsuario);
        } else if (actionEvent.getSource().equals(btnLivroAdd)) {
            livroAdd();
        } else if (actionEvent.getSource().equals(btnLivroClear)) {
            livroClear();
        } else if (actionEvent.getSource().equals(btnLivroCancel)) {
            livroClear();
            panMain.setVisible(true);
            this.remove(panLivro);
        }
    }

    public AdminGUI() {
        super("Pagina do admin");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Configura o painel principal
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

        // Configura o painel de add usuario
        panUsuario = new JPanel(new GridLayout(0, 1));

        txtUserNome = new JTextField("NOME");
        panUsuario.add(txtUserNome);

        txtUserUsername = new JTextField();
        txtUserUsername.setToolTipText("Username");
        panUsuario.add(txtUserUsername);

        txtUserPassword = new JTextField();
        txtUserPassword.setToolTipText("Password");
        panUsuario.add(txtUserPassword);

        txtUserFoto = new JTextField();
        txtUserUsername.setToolTipText("Foto");
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

        // Configura o painel de adicionar um emprestimo para a biblioteca
        panEmprestar = new JPanel(null);

        // Configura o painel de adicionar livro
        panLivro = new JPanel(new GridLayout(0, 1));

        txtLivroNome = new JTextField();
        txtLivroNome.setToolTipText("Nome");
        panLivro.add(txtLivroNome);

        txtLivroAutor = new JTextField();
        txtLivroNome.setToolTipText("Autor");
        panLivro.add(txtLivroAutor);

        txtLivroCatId = new JTextField();
        txtLivroCatId.setToolTipText("Id Cat.");
        txtLivroCatId.setEnabled(false);
        panLivro.add(txtLivroCatId);

        txtLivroCatNome = new JTextField();
        txtLivroCatNome.setToolTipText("Nome Cat.");
        panLivro.add(txtLivroCatNome);

        txtLivroFoto = new JTextField();
        txtLivroFoto.setToolTipText("Foto");
        panLivro.add(txtLivroFoto);

        btnLivroAdd = new JButton("Adicionar");
        btnLivroAdd.addActionListener(this);
        panLivro.add(btnLivroAdd);

        btnLivroClear = new JButton("Limpar");
        btnLivroClear.addActionListener(this);
        panLivro.add(btnLivroClear);

        btnLivroCancel = new JButton("Cancelar");
        btnLivroCancel.addActionListener(this);
        panLivro.add(btnLivroCancel);

        // Configura o painel de Adicionar Categoria
        panCategoria = new JPanel(new GridLayout(0, 1));
        txtCategoriaNome = new JTextField();
        txtCategoriaNome.setToolTipText("Nome");





    }
}
