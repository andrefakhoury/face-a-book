package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import banco_dados.ConexaoBanco;
import content.*;

public class AdminGUI extends JFrame implements ActionListener {
//    // Geral
//    private JPanel panMain, panLivro, panEmprestar;
//    private JButton btnAddLivro, btnAddEmprestimo;
//    private JButton btnEditUsuario, btnEditLivro, btnEditCategoria, btnEditEmprestimo;
//    private JButton btnConfirmaEmprestimo, btnConfirmaDevolucao, btnPegarLivroDeVolta;
//
//    // Emprestimo
//    private JButton btnEmprestimoAdd, btnEmprestimoClear, btnEmprestimoCancel;
//    private JComboBox cmbEmprestimoUsuario, cmbEmprestimoLivro;
//    private JTextField txtEmprestimoData;
//    private JButton btnEmprestimoLivro, btnEmprestimoUsuario;
//
//    private boolean isAddLivro = false, isAddEmprestimo = false;
//
//    private void emprestimoClear() {
//        txtEmprestimoData.setText("01/01/2010");
//        cmbEmprestimoUsuario.setSelectedIndex(0);
//        cmbEmprestimoLivro.setSelectedIndex(0);
//    }
//
//    private void emprestimoAdd() {
//        Livro livro = (Livro) cmbEmprestimoLivro.getSelectedItem();
//        Usuario usuario = (Usuario) cmbEmprestimoUsuario.getSelectedItem();
//        String data = txtEmprestimoData.getText();
//
//        if (livro == null || usuario == null || data == null || data.equals("")) {
//            JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        int idLivro = livro.getId();
//        int idUsuario = usuario.getId();
//
//        String novaData;
//        java.sql.Date date;
//
//        try {
//            novaData = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)).format
//                    (DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US));
//            date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(novaData).getTime());
//
//            if (date.before(new Date())) throw new Exception("Data inferior ao dia atual");
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Data invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        ConexaoBanco conexaoBanco = new ConexaoBanco();
//        conexaoBanco.connect();
//        boolean added = conexaoBanco.insertDisponibilidade(idLivro, idUsuario, date);
//        conexaoBanco.disconnect();
//
//        if (added) {
//            JOptionPane.showMessageDialog(this, "Inserido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
//
//            emprestimoClear();
//
//            panMain.setVisible(true);
//
//            isAddEmprestimo = false;
//            this.remove(panEmprestar);
//        }
//    }
//
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
//        if (actionEvent.getSource().equals(btnAddLivro) || actionEvent.getSource().equals(btnEmprestimoLivro)) {
//            if (isAddEmprestimo) {
//                panEmprestar.setVisible(false);
//            } else {
//                panMain.setVisible(false);
//            }
//
//            this.add(panLivro);
//            isAddLivro = true;
//        } else if (actionEvent.getSource().equals(btnAddEmprestimo)) {
//            panMain.setVisible(false);
//            this.add(panEmprestar);
//            isAddEmprestimo = true;
//        } else if (actionEvent.getSource().equals(btnConfirmaDevolucao)) {
//            DevolucaoGUI devolucaoGUI = new DevolucaoGUI();
//        } else if (actionEvent.getSource().equals(btnConfirmaEmprestimo)) {
//            ConfirmaEmprestimoGUI confirmaEmprestimoGUI = new ConfirmaEmprestimoGUI();
//        } else if (actionEvent.getSource().equals(btnPegarLivroDeVolta)) {
//            RetirarLivroGUI retirarLivroGUI = new RetirarLivroGUI();
//        }
//
//        // Botoes de editar dados
//        else if (actionEvent.getSource().equals(btnEditUsuario)) {
//            new EditUsuarioGUI();
//        } else if (actionEvent.getSource().equals(btnEditCategoria)) {
//            new EditCategoriaGUI();
//        } else if (actionEvent.getSource().equals(btnEditLivro)) {
//            new EditLivroGUI();
//        }
//
//        // Botoes especificos do Adicionar Emprestimo
//        else if (actionEvent.getSource().equals(btnEmprestimoAdd)) {
//            emprestimoAdd();
//        } else if (actionEvent.getSource().equals(btnEmprestimoClear)) {
//            emprestimoClear();
//        } else if (actionEvent.getSource().equals(btnEmprestimoCancel)) {
//            emprestimoClear();
//            panMain.setVisible(true);
//            this.remove(panEmprestar);
//            isAddEmprestimo = false;
//        }
    }

    public AdminGUI() {
//        super("Pagina do admin");
//        this.setSize(920, 720);
//        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.setLocationRelativeTo(null);
//
//        // Configura o painel principal
//        panMain = new JPanel(null);
//
//        btnAddLivro = new JButton("Adicionar livro");
//        btnAddLivro.setBounds(10, 70, 200, 50);
//        btnAddLivro.addActionListener(this);
//        panMain.add(btnAddLivro);
//
//        btnAddEmprestimo = new JButton("Adicionar emprestimo");
//        btnAddEmprestimo.setBounds(10, 130, 200, 50);
//        btnAddEmprestimo.addActionListener(this);
//        panMain.add(btnAddEmprestimo);
//
//        btnEditUsuario = new JButton("Editar usuario");
//        btnEditUsuario.setBounds(220, 10, 200, 50);
//        btnEditUsuario.addActionListener(this);
//        panMain.add(btnEditUsuario);
//
//        btnEditLivro = new JButton("Editar livro");
//        btnEditLivro.setBounds(220, 70, 200, 50);
//        btnEditLivro.addActionListener(this);
//        panMain.add(btnEditLivro);
//
//        btnEditCategoria = new JButton("Editar categoria");
//        btnEditCategoria.setBounds(220, 190, 200, 50);
//        btnEditCategoria.addActionListener(this);
//        panMain.add(btnEditCategoria);
//
//        btnConfirmaEmprestimo = new JButton("Confirmar emprestimo");
//        btnConfirmaEmprestimo.setBounds(10, 260, 200, 50);
//        btnConfirmaEmprestimo.addActionListener(this);
//        panMain.add(btnConfirmaEmprestimo);
//
//        btnConfirmaDevolucao = new JButton("Confirmar devolucao");
//        btnConfirmaDevolucao.setBounds(10, 320, 200, 50);
//        btnConfirmaDevolucao.addActionListener(this);
//        panMain.add(btnConfirmaDevolucao);
//
//        btnPegarLivroDeVolta = new JButton("Devolver livro que emprestaram a biblioteca");
//        btnPegarLivroDeVolta.setBounds(10, 380, 200, 50);
//        btnPegarLivroDeVolta.addActionListener(this);
//        panMain.add(btnPegarLivroDeVolta);
//
//        this.add(panMain);
//        this.setVisible(true);
//
//        // Configura o painel de adicionar um emprestimo para a biblioteca
//        panEmprestar = new JPanel(new GridLayout(0, 1));
//
//        cmbEmprestimoLivro = new JComboBox();
//        panEmprestar.add(cmbEmprestimoLivro);
//
//        btnEmprestimoLivro = new JButton("Adicionar Livro");
//        btnEmprestimoLivro.addActionListener(this);
//        panEmprestar.add(btnEmprestimoLivro);
//
//        cmbEmprestimoUsuario = new JComboBox();
//        panEmprestar.add(cmbEmprestimoUsuario);
//
//        btnEmprestimoUsuario = new JButton("Adicionar Usuario");
//        btnEmprestimoUsuario.addActionListener(this);
//        panEmprestar.add(btnEmprestimoUsuario);
//
//        txtEmprestimoData = new JTextField("01/01/2010");
//        panEmprestar.add(txtEmprestimoData);
//
//        btnEmprestimoAdd = new JButton("Adicionar");
//        btnEmprestimoAdd.addActionListener(this);
//        panEmprestar.add(btnEmprestimoAdd);
//
//        btnEmprestimoClear = new JButton("Limpar");
//        btnEmprestimoClear.addActionListener(this);
//        panEmprestar.add(btnEmprestimoClear);
//
//        btnEmprestimoCancel = new JButton("Cancelar");
//        btnEmprestimoCancel.addActionListener(this);
//        panEmprestar.add(btnEmprestimoCancel);
//
//        // Configura o painel de adicionar livro
//        panLivro = new JPanel(new GridLayout(0, 1));
//
//        txtLivroNome = new JTextField();
//        txtLivroNome.setToolTipText("Nome");
//        panLivro.add(txtLivroNome);
//
//        txtLivroAutor = new JTextField();
//        txtLivroNome.setToolTipText("Autor");
//        panLivro.add(txtLivroAutor);
//
//        cmbLivroCat = new JComboBox();
//        panLivro.add(cmbLivroCat);
//
//        btnLivroCategoria = new JButton("Adicionar Categoria");
//        btnLivroCategoria.addActionListener(this);
//        panLivro.add(btnLivroCategoria);
//
//        txtLivroFoto = new JTextField();
//        txtLivroFoto.setToolTipText("Foto");
//        panLivro.add(txtLivroFoto);
//
//        btnLivroAdd = new JButton("Adicionar");
//        btnLivroAdd.addActionListener(this);
//        panLivro.add(btnLivroAdd);
//
//        btnLivroClear = new JButton("Limpar");
//        btnLivroClear.addActionListener(this);
//        panLivro.add(btnLivroClear);
//
//        btnLivroCancel = new JButton("Cancelar");
//        btnLivroCancel.addActionListener(this);
//        panLivro.add(btnLivroCancel);
    }
}
