package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import banco_dados.ConexaoBanco;
import content.*;

public class AdminGUI extends JFrame implements ActionListener {
    // Geral
    private JPanel panMain, panUsuario, panLivro, panCategoria, panEmprestar;
    private JButton btnAddUsuario, btnAddLivro, btnAddCategoria, btnAddEmprestimo;
    private JButton btnConfirmaEmprestimo, btnConfirmaDevolucao, btnPegarLivroDeVolta;

    // Usuario
    private JButton btnUserAdd, btnUserClear, btnUserCancel;
    private JTextField txtUserNome, txtUserUsername, txtUserPassword, txtUserFoto;
    private JCheckBox ckbUserAdmin;

    // Livro
    private JButton btnLivroAdd, btnLivroClear, btnLivroCancel;
    private JTextField txtLivroNome, txtLivroAutor, txtLivroFoto;
    private JComboBox cmbLivroCat;
    private JButton btnLivroCategoria;

    // Categoria
    private JButton btnCategoriaAdd, btnCategoriaClear, btnCategoriaCancel;
    private JTextField txtCategoriaNome;

    // Emprestimo
    private JButton btnEmprestimoAdd, btnEmprestimoClear, btnEmprestimoCancel;
    private JComboBox cmbEmprestimoUsuario, cmbEmprestimoLivro;
    private JTextField txtEmprestimoData;
    private JButton btnEmprestimoLivro, btnEmprestimoUsuario;

    private boolean isAddLivro = false, isAddEmprestimo = false;

    private void updateComboCategoria() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Categoria> categorias = conexaoBanco.getCategorias();
        conexaoBanco.disconnect();
        cmbLivroCat.removeAllItems();
        for (Categoria categoria : categorias) {
            cmbLivroCat.addItem(categoria);
        }
    }

    public void updateComboLivros() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Livro> livros = conexaoBanco.getLivros();
        conexaoBanco.disconnect();
        cmbEmprestimoLivro.removeAllItems();
        for (Livro livro : livros) {
            cmbEmprestimoLivro.addItem(livro);
        }
    }

    public void updateComboUsuarios() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Usuario> usuarios = conexaoBanco.getUsuarios();
        conexaoBanco.disconnect();
        cmbEmprestimoUsuario.removeAllItems();
        for (Usuario usuario : usuarios) {
            cmbEmprestimoUsuario.addItem(usuario);
        }
    }

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
                JOptionPane.showMessageDialog(this, "Inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                updateComboUsuarios();
                userClear();

                if (isAddEmprestimo) {
                    panEmprestar.setVisible(true);
                    cmbEmprestimoUsuario.setSelectedIndex(cmbEmprestimoUsuario.getItemCount()-1);
                } else {
                    panMain.setVisible(true);
                }

                this.remove(panUsuario);
            }
        }
    }

    private void livroClear() {
        txtLivroFoto.setText("");
        cmbLivroCat.setSelectedIndex(0);
        txtLivroAutor.setText("");
        txtLivroAutor.setText("");
        txtLivroNome.setText("");
    }

    private void livroAdd() {
        Livro livro = new Livro();

        String nome = txtLivroNome.getText();
        String autor = txtLivroAutor.getText();
        String foto = txtLivroFoto.getText();
        Categoria cat = (Categoria) cmbLivroCat.getSelectedItem();

        if (nome == null || nome.equals("") || autor == null || autor.equals("") || foto == null || foto.equals("") || cat == null) {
            JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        livro.setNome(nome);
        livro.setAutor(autor);
        livro.setFoto(foto);
        livro.setCategoria(cat.getId(), cat.getNome());

        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        boolean added = conexaoBanco.insertLivro(livro);
        conexaoBanco.disconnect();

        if (added) {
            JOptionPane.showMessageDialog(this, "Inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            updateComboLivros();
            livroClear();

            if (isAddEmprestimo) {
                panEmprestar.setVisible(true);
                cmbEmprestimoLivro.setSelectedIndex(cmbEmprestimoLivro.getItemCount()-1);
            } else {
                panMain.setVisible(true);
            }

            isAddLivro = false;
            this.remove(panLivro);
        }

    }

    private void categoriaClear() {
        txtCategoriaNome.setText("");
    }

    private void categoriaAdd() {
        if (txtCategoriaNome.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nome invalido!", "Falha", JOptionPane.WARNING_MESSAGE);
        } else {
            Categoria categoria = new Categoria(txtCategoriaNome.getText());
            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();

            if (conexaoBanco.insertCategoria(categoria)) {
                JOptionPane.showMessageDialog(this, "Inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                updateComboCategoria();
                categoriaClear();
                if (isAddLivro) {
                    panLivro.setVisible(true);
                    cmbLivroCat.setSelectedIndex(cmbLivroCat.getItemCount()-1);
                } else {
                    panMain.setVisible(true);
                }
                this.remove(panCategoria);
            }
            conexaoBanco.disconnect();
        }
    }

    private void emprestimoClear() {
        txtEmprestimoData.setText("01/01/2010");
        cmbEmprestimoUsuario.setSelectedIndex(0);
        cmbEmprestimoLivro.setSelectedIndex(0);
    }

    private void emprestimoAdd() {
//        String livro = cmbEmprestimoLivro.getSelectedItem() == null ? null : cmbEmprestimoLivro.getSelectedItem().toString();
//        String usuario = cmbEmprestimoUsuario.getSelectedItem() == null ? null : cmbEmprestimoUsuario.getSelectedItem().toString();

        Livro livro = (Livro) cmbEmprestimoLivro.getSelectedItem();
        Usuario usuario = (Usuario) cmbEmprestimoUsuario.getSelectedItem();
        String data = txtEmprestimoData.getText();

        if (livro == null || usuario == null || data == null || data.equals("")) {
            JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idLivro = livro.getId();
        int idUsuario = usuario.getId();

        String novaData;
        java.sql.Date date;

        try {
            novaData = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)).format
                    (DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US));
            date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(novaData).getTime());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        boolean added = conexaoBanco.insertDisponibilidade(idLivro, idUsuario, date);
        conexaoBanco.disconnect();

        if (added) {
            JOptionPane.showMessageDialog(this, "Inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            emprestimoClear();

            panMain.setVisible(true);

            isAddEmprestimo = false;
            this.remove(panEmprestar);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnAddUsuario) || actionEvent.getSource().equals(btnEmprestimoUsuario)) {
            if (isAddEmprestimo) {
                panEmprestar.setVisible(false);
            } else {
                panMain.setVisible(false);
            }

            this.add(panUsuario);
        } else if (actionEvent.getSource().equals(btnAddLivro) || actionEvent.getSource().equals(btnEmprestimoLivro)) {
            if (isAddEmprestimo) {
                panEmprestar.setVisible(false);
            } else {
                panMain.setVisible(false);
            }

            this.add(panLivro);
            isAddLivro = true;
        } else if (actionEvent.getSource().equals(btnAddCategoria) || actionEvent.getSource().equals(btnLivroCategoria)) {
            if (panLivro.isVisible()) {
                panLivro.setVisible(false);
            } else if (panMain.isVisible()) {
                panMain.setVisible(false);
            }

            this.add(panCategoria);
        } else if (actionEvent.getSource().equals(btnAddEmprestimo)) {
            panMain.setVisible(false);
            this.add(panEmprestar);
            isAddEmprestimo = true;
        } else if (actionEvent.getSource().equals(btnConfirmaDevolucao)) {
            DevolucaoGUI devolucaoGUI = new DevolucaoGUI();
        } else if (actionEvent.getSource().equals(btnConfirmaEmprestimo)) {
            ConfirmaEmprestimoGUI confirmaEmprestimoGUI = new ConfirmaEmprestimoGUI();
        } else if (actionEvent.getSource().equals(btnPegarLivroDeVolta)) {
            RetirarLivroGUI retirarLivroGUI = new RetirarLivroGUI();
        }

        // Botoes especificos do Adicionar Usuario
        else if (actionEvent.getSource().equals(btnUserAdd)) {
            userAdd();
        } else if (actionEvent.getSource().equals(btnUserClear)) {
            userClear();
        } else if (actionEvent.getSource().equals(btnUserCancel)) {
            userClear();
            panMain.setVisible(true);
            this.remove(panUsuario);
        }

        // Botoes especificos do Adicionar Livro
        else if (actionEvent.getSource().equals(btnLivroAdd)) {
            livroAdd();
        } else if (actionEvent.getSource().equals(btnLivroClear)) {
            livroClear();
        } else if (actionEvent.getSource().equals(btnLivroCancel)) {
            livroClear();
            isAddLivro = false;
            panMain.setVisible(true);
            this.remove(panLivro);
        }

        // Botoes especificos do Adicionar Categoria
        else if (actionEvent.getSource().equals(btnCategoriaAdd)) {
            categoriaAdd();
        } else if (actionEvent.getSource().equals(btnCategoriaClear)) {
            categoriaClear();
        } else if (actionEvent.getSource().equals(btnCategoriaCancel)) {
            categoriaClear();

            if (isAddLivro) {
                panLivro.setVisible(true);
            } else {
                panMain.setVisible(true);
            }

            this.remove(panCategoria);
        }

        // Botoes especificos do Adicionar Emprestimo
        else if (actionEvent.getSource().equals(btnEmprestimoAdd)) {
            emprestimoAdd();
        } else if (actionEvent.getSource().equals(btnEmprestimoClear)) {
            emprestimoClear();
        } else if (actionEvent.getSource().equals(btnEmprestimoCancel)) {
            emprestimoClear();
            panMain.setVisible(true);
            this.remove(panEmprestar);
            isAddEmprestimo = false;
        }
    }

    public AdminGUI() {
        super("Pagina do admin");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

        btnConfirmaEmprestimo = new JButton("Confirmar emprestimo");
        btnConfirmaEmprestimo.setBounds(10, 260, 200, 50);
        btnConfirmaEmprestimo.addActionListener(this);
        panMain.add(btnConfirmaEmprestimo);

        btnConfirmaDevolucao = new JButton("Confirmar devolucao");
        btnConfirmaDevolucao.setBounds(10, 320, 200, 50);
        btnConfirmaDevolucao.addActionListener(this);
        panMain.add(btnConfirmaDevolucao);

        btnPegarLivroDeVolta = new JButton("Devolver livro que emprestaram a biblioteca");
        btnPegarLivroDeVolta.setBounds(10, 380, 200, 50);
        btnPegarLivroDeVolta.addActionListener(this);
        panMain.add(btnPegarLivroDeVolta);

        this.add(panMain);
        this.setVisible(true);

        // Configura o painel de add usuario
        panUsuario = new JPanel(new GridLayout(0, 1));

        txtUserNome = new JTextField();
        txtUserNome.setToolTipText("Nome");
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
        panEmprestar = new JPanel(new GridLayout(0, 1));

        cmbEmprestimoLivro = new JComboBox();
        panEmprestar.add(cmbEmprestimoLivro);
        updateComboLivros();

        btnEmprestimoLivro = new JButton("Adicionar Livro");
        btnEmprestimoLivro.addActionListener(this);
        panEmprestar.add(btnEmprestimoLivro);

        cmbEmprestimoUsuario = new JComboBox();
        panEmprestar.add(cmbEmprestimoUsuario);
        updateComboUsuarios();

        btnEmprestimoUsuario = new JButton("Adicionar Usuario");
        btnEmprestimoUsuario.addActionListener(this);
        panEmprestar.add(btnEmprestimoUsuario);

        txtEmprestimoData = new JTextField("01/01/2010");
        panEmprestar.add(txtEmprestimoData);

        btnEmprestimoAdd = new JButton("Adicionar");
        btnEmprestimoAdd.addActionListener(this);
        panEmprestar.add(btnEmprestimoAdd);

        btnEmprestimoClear = new JButton("Limpar");
        btnEmprestimoClear.addActionListener(this);
        panEmprestar.add(btnEmprestimoClear);

        btnEmprestimoCancel = new JButton("Cancelar");
        btnEmprestimoCancel.addActionListener(this);
        panEmprestar.add(btnEmprestimoCancel);

        // Configura o painel de adicionar livro
        panLivro = new JPanel(new GridLayout(0, 1));

        txtLivroNome = new JTextField();
        txtLivroNome.setToolTipText("Nome");
        panLivro.add(txtLivroNome);

        txtLivroAutor = new JTextField();
        txtLivroNome.setToolTipText("Autor");
        panLivro.add(txtLivroAutor);

        cmbLivroCat = new JComboBox();
        updateComboCategoria();
        panLivro.add(cmbLivroCat);

        btnLivroCategoria = new JButton("Adicionar Categoria");
        btnLivroCategoria.addActionListener(this);
        panLivro.add(btnLivroCategoria);

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
        panCategoria.add(txtCategoriaNome);

        btnCategoriaAdd = new JButton("Adicionar");
        btnCategoriaAdd.addActionListener(this);
        panCategoria.add(btnCategoriaAdd);

        btnCategoriaClear = new JButton("Limpar");
        btnCategoriaClear.addActionListener(this);
        panCategoria.add(btnCategoriaClear);

        btnCategoriaCancel = new JButton("Cancelar");
        btnCategoriaCancel.addActionListener(this);
        panCategoria.add(btnCategoriaCancel);
    }
}
