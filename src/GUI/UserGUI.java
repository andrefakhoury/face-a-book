package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import banco_dados.ConexaoBanco;
import content.*;

//JFrame para página do usuário
public class UserGUI extends JFrame implements ActionListener {
    private JButton btnAdmin, btnPegarLivro, btnLogout, btnEditarPerfil;
    private JComboBox cmbLivrosPegos, cmbLivrosEmprestados;
    private JLabel lblInfo, lblBemVindo, lblFoto;
    private Usuario usuario;

    private void updateInformacoes() {//Método para atualizar itens da janela após alguma alteração
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        usuario = conexaoBanco.getUsuario(usuario.getUsername(), null);
        conexaoBanco.disconnect();

        lblFoto.setIcon(new ImageIcon(new ImageIcon(usuario.getFoto()).getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT)));
        lblBemVindo.setText("Bem vindo(a), " + usuario.getNome() + "!");
        lblInfo.setText(usuario.toString());

        updateComboLivrosEmprestados();
        updateComboLivrosPegos();
    }

    private void updateComboLivrosPegos() {//Método para exibir livros pegos pelo usuário
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Emprestimo> emprestimos = conexaoBanco.getEmprestimos(usuario);
        conexaoBanco.disconnect();

        cmbLivrosPegos.removeAllItems();
        for (Emprestimo emprestimo : emprestimos) {
            cmbLivrosPegos.addItem(emprestimo);
        }
    }

    private void updateComboLivrosEmprestados() {//Método para exibir livros emprestados pelo usuário
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Disponibilidade> disponibilidades = conexaoBanco.getDisponibilidades(usuario);
        conexaoBanco.disconnect();

        cmbLivrosEmprestados.removeAllItems();
        for (Disponibilidade disponibilidade : disponibilidades) {
            cmbLivrosEmprestados.addItem(disponibilidade);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnPegarLivro)) {//Ação ao "Consultar livros"
            BuscaLivroGUI buscaLivroGUI = new BuscaLivroGUI(usuario);
            updateInformacoes();
        } else if (actionEvent.getSource().equals(btnAdmin)) {//Ação ao "Área do Admin"
            new AdminGUI();
            updateInformacoes();
        } else if (actionEvent.getSource().equals(btnLogout)) {//Ação ao "Sair"
            this.setVisible(false);
            this.dispose();
            LoginGUI loginGUI = new LoginGUI();
        } else if (actionEvent.getSource().equals(btnEditarPerfil)) {//Ação ao "Editar perfil"
            new EditarPerfilGUI(usuario);
            updateInformacoes();
        }
    }

    //INÍCIO - Construtor de UserGUI
    public UserGUI(Usuario usuario) {
        //INÍCIO - Configuração da janela
        super("Pagina do usuario");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.usuario = usuario;

        JPanel panMain = new JPanel(null);
        //FIM - Configuração da janela
        
        //INÍCIO - Intanciação e inserção de itens da janela
        lblFoto = new JLabel();
        lblFoto.setBounds(700, 10, 200, 230);
        lblFoto.setIcon(new ImageIcon(new ImageIcon(usuario.getFoto()).getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT)));
        panMain.add(lblFoto);

        lblBemVindo = new JLabel("Bem vindo(a), " + usuario.getNome() + "!");
        lblBemVindo.setBounds(10, 10, 300, 20);
        panMain.add(lblBemVindo);

        lblInfo = new JLabel(usuario.toString());
        lblInfo.setBounds(700, 245, 300, 20);
        panMain.add(lblInfo);

        JLabel lblEmprestimos = new JLabel("Livros para devolver");
        lblEmprestimos.setBounds(10, 35, 300, 20);
        panMain.add(lblEmprestimos);

        JPanel panEmprestimos = new JPanel(new GridLayout());
        panEmprestimos.setBounds(10, 40, 600, 300);
        panEmprestimos.setBackground(new Color(0xe1e1e1));
        panMain.add(panEmprestimos);

        cmbLivrosPegos = new JComboBox();
        panEmprestimos.add(cmbLivrosPegos);
        updateComboLivrosPegos();

        JLabel lblEmprestados = new JLabel("Livros meus na biblioteca");
        lblEmprestados.setBounds(10, 355, 300, 20);
        panMain.add(lblEmprestados);

        JPanel panEmprestados = new JPanel(new GridLayout());
        panEmprestados.setBounds(10, 360, 600, 300);
        panEmprestados.setBackground(new Color(0xe1e1e1));
        panMain.add(panEmprestados);

        cmbLivrosEmprestados = new JComboBox();
        panEmprestados.add(cmbLivrosEmprestados);
        updateComboLivrosEmprestados();

        btnEditarPerfil = new JButton("Editar perfil");
        btnEditarPerfil.setBounds(700, 300, 200, 50);
        btnEditarPerfil.addActionListener(this);
        panMain.add(btnEditarPerfil);

        btnPegarLivro = new JButton("Consultar livros");
        btnPegarLivro.setBounds(700, 500, 200, 50);
        btnPegarLivro.addActionListener(this);
        panMain.add(btnPegarLivro);

        if (usuario.isAdmin()) {//Verificação se usuário é administrador
            btnAdmin = new JButton("Area do Admin");
            btnAdmin.setBounds(700, 555, 200, 50);
            btnAdmin.addActionListener(this);
            panMain.add(btnAdmin);
        }

        btnLogout = new JButton("Sair");
        btnLogout.setBounds(700, 620, 200, 50);
        btnLogout.addActionListener(this);
        panMain.add(btnLogout);

        this.add(panMain);
        this.setVisible(true);
        //FIM - Intanciação e inserção de itens da janela
    }//FIM - Construtor de UserGUI
}
