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

public class AdminGUI extends JDialog implements ActionListener {
    // Geral
    private JPanel panMain;

    private JButton btnEditUsuario, btnEditLivro, btnEditCategoria, btnEditEmprestimo;
    private JButton btnConfirmaEmprestimo, btnConfirmaDevolucao, btnPegarLivroDeVolta;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnEditLivro)) {
            new EditLivroGUI();
        } else if (actionEvent.getSource().equals(btnEditCategoria)) {
            new EditCategoriaGUI();
        } else if (actionEvent.getSource().equals(btnEditUsuario)) {
            new EditUsuarioGUI();
        } else if (actionEvent.getSource().equals(btnEditEmprestimo)) {
            new EditEmprestimoGUI();
        } else if (actionEvent.getSource().equals(btnConfirmaDevolucao)) {
            new DevolucaoGUI();
        } else if (actionEvent.getSource().equals(btnConfirmaEmprestimo)) {
            new ConfirmaEmprestimoGUI();
        } else if (actionEvent.getSource().equals(btnPegarLivroDeVolta)) {
            new RetirarLivroGUI();
        }
    }

    public AdminGUI() {
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        // Configura o painel principal
        panMain = new JPanel(null);

        btnEditUsuario = new JButton("Editar usuario");
        btnEditUsuario.setBounds(220, 10, 200, 50);
        btnEditUsuario.addActionListener(this);
        panMain.add(btnEditUsuario);

        btnEditLivro = new JButton("Editar livro");
        btnEditLivro.setBounds(220, 70, 200, 50);
        btnEditLivro.addActionListener(this);
        panMain.add(btnEditLivro);

        btnEditEmprestimo = new JButton("Editar emprestimo");
        btnEditEmprestimo.setBounds(220, 130, 200, 50);
        btnEditEmprestimo.addActionListener(this);
        panMain.add(btnEditEmprestimo);

        btnEditCategoria = new JButton("Editar categoria");
        btnEditCategoria.setBounds(220, 190, 200, 50);
        btnEditCategoria.addActionListener(this);
        panMain.add(btnEditCategoria);

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
    }
}
