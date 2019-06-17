package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import content.*;

public class UserGUI extends JFrame implements ActionListener {
    private JButton btnAdmin, btnPegarLivro;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnPegarLivro)) {
            BuscaLivroGUI buscaLivroGUI = new BuscaLivroGUI();
        } else if (actionEvent.getSource().equals(btnAdmin)) {
            AdminGUI adminGUI = new AdminGUI();
        }
    }

    public UserGUI(Usuario usuario) {
        super("Pagina do usuario");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panMain = new JPanel(null);

//        ImageIcon icon = new ImageIcon();
//        Image im;
//
//        try {
//            icon = new ImageIcon(file);
//        } catch (Exception ex) {
//            icon = new ImageIcon("./images/ERROR.jpg");
//        } finally {
//            im = icon.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
//        }
//
//        return new ImageIcon(im);

        JLabel lblFoto = new JLabel();
        lblFoto.setBounds(700, 10, 200, 230);
        lblFoto.setIcon(new ImageIcon(new ImageIcon(usuario.getFoto()).getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT)));
        panMain.add(lblFoto);

        JLabel lblBemVindo = new JLabel("Bem vindo(a), " + usuario.getNome() + "!");
        lblBemVindo.setBounds(10, 10, 300, 20);
        panMain.add(lblBemVindo);

        JLabel lblInfo = new JLabel(usuario.toString());
        lblInfo.setBounds(700, 245, 300, 20);
        panMain.add(lblInfo);

        JLabel lblEmprestimos = new JLabel("Livros para devolver");
        lblEmprestimos.setBounds(10, 35, 300, 20);
        panMain.add(lblEmprestimos);

        JPanel panEmprestimos = new JPanel(null);
        panEmprestimos.setBounds(10, 40, 600, 300);
        panEmprestimos.setBackground(new Color(0xe1e1e1));
        panMain.add(panEmprestimos);

        JLabel lblEmprestados = new JLabel("Livros meus na biblioteca");
        lblEmprestados.setBounds(10, 355, 300, 20);
        panMain.add(lblEmprestados);

        JPanel panEmprestados = new JPanel(null);
        panEmprestados.setBounds(10, 360, 600, 300);
        panEmprestados.setBackground(new Color(0xe1e1e1));
        panMain.add(panEmprestados);

        btnPegarLivro = new JButton("Pegar um livro");
        btnPegarLivro.setBounds(700, 500, 200, 50);
        btnPegarLivro.addActionListener(this);
        panMain.add(btnPegarLivro);

        if (usuario.isAdmin()) {
            btnAdmin = new JButton("Area do Admin");
            btnAdmin.setBounds(700, 555, 200, 50);
            btnAdmin.addActionListener(this);
            panMain.add(btnAdmin);
        }

        this.add(panMain);
        this.setVisible(true);

    }


}
