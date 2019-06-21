package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;

import banco_dados.ConexaoBanco;
import content.*;

/**
 * Subclasse de JFrame - interface grafica da pagina do usuario
 */
public class UserGUI extends JFrame implements ActionListener {
    // usuario atual
    private Usuario usuario;

    // objetos swing
    private JButton btnAdmin, btnEditarPerfil, btnLogout, btnPegarLivro;
    private JComboBox cmbLivrosEmprestados, cmbLivrosPegos;
    private JLabel lblLogo, lblDevolver, lblBiblioteca;
    private JLabel lblBemVindo, lblFoto, lblInfo, lblTitulo;
    private JPanel panMain;

    /**
     * Método para atualizar itens da janela após alguma alteração
     */
    private void updateInformacoes() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        usuario = conexaoBanco.getUsuario(usuario.getUsername(), null);
        conexaoBanco.disconnect();

        lblFoto.setIcon(new ImageIcon(new ImageIcon(usuario.getFoto()).getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT)));
        lblBemVindo.setText(usuario.getNome());
        lblInfo.setText(usuario.toString());

        updateComboLivrosEmprestados();
        updateComboLivrosPegos();
    }

    /**
     * Atualiza o combo box com os livros pegos pelo usuario
     */
    private void updateComboLivrosPegos() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Emprestimo> emprestimos = conexaoBanco.getEmprestimos(usuario);
        conexaoBanco.disconnect();

        cmbLivrosPegos.removeAllItems();
        for (Emprestimo emprestimo : emprestimos) {
            cmbLivrosPegos.addItem(emprestimo);
        }
    }

    /**
     * Atualiza o combo box com os livros emprestados pelo usuario
     */
    private void updateComboLivrosEmprestados() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Disponibilidade> disponibilidades = conexaoBanco.getDisponibilidades(usuario);
        conexaoBanco.disconnect();

        cmbLivrosEmprestados.removeAllItems();
        for (Disponibilidade disponibilidade : disponibilidades) {
            cmbLivrosEmprestados.addItem(disponibilidade);
        }
    }

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnPegarLivro)) { //Ação ao "Consultar livros"
            BuscaLivroGUI buscaLivroGUI = new BuscaLivroGUI(usuario);
            updateInformacoes();
        } else if (actionEvent.getSource().equals(btnAdmin)) { //Ação ao "Área do Admin"
            new AdminGUI();
            updateInformacoes();
        } else if (actionEvent.getSource().equals(btnLogout)) { //Ação ao "Sair"
            this.setVisible(false);
            this.dispose();
            LoginGUI loginGUI = new LoginGUI();
        } else if (actionEvent.getSource().equals(btnEditarPerfil)) {//Ação ao "Editar perfil"
            new EditarPerfilGUI(usuario);
            updateInformacoes();
        }
    }

    /**
     * Inicializa os componentes java swing
     * Criado automaticamente pelo NetBeans
     */
    private void initComponents() {
        panMain = new JPanel();
        lblLogo = new JLabel();
        lblFoto = new JLabel();
        lblInfo = new JLabel();
        lblBemVindo = new JLabel();
        lblDevolver = new JLabel();
        btnLogout = new JButton();
        btnEditarPerfil = new JButton();
        btnPegarLivro = new JButton();
        btnAdmin = new JButton();
        cmbLivrosPegos = new JComboBox<>();
        lblTitulo = new JLabel();
        cmbLivrosEmprestados = new JComboBox<>();
        lblBiblioteca = new JLabel();

        setPreferredSize(new java.awt.Dimension(926, 700));
        setResizable(false);
        getContentPane().setLayout(null);

        panMain.setBackground(new java.awt.Color(33, 64, 140));

        lblLogo.setBounds(0, 0, 920, 30);
        lblLogo.setIcon(new ImageIcon(Paths.get("").toAbsolutePath() + "/images/face-a-book-branco.png"));

        GroupLayout panMainLayout = new GroupLayout(panMain);
        panMain.setLayout(panMainLayout);
        panMainLayout.setHorizontalGroup(
                panMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, panMainLayout.createSequentialGroup()
                                .addGap(361, 361, 361)
                                .addComponent(lblLogo, GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                                .addGap(362, 362, 362))
        );
        panMainLayout.setVerticalGroup(
                panMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblLogo, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        );
        panMain.setBounds(0, 0, 926, 70);
        getContentPane().add(panMain);

        lblFoto.setBounds(50, 120, 200, 220);
        getContentPane().add(lblFoto);

        lblInfo.setFont(new java.awt.Font("Exo ExtraBold", 0, 18)); // NOI18N
        lblInfo.setForeground(new java.awt.Color(33, 64, 140));
        lblInfo.setText("(1) Administrador - admin");
        lblInfo.setBounds(300, 160, 926, 30);
        getContentPane().add(lblInfo);

        lblBemVindo.setFont(new java.awt.Font("Exo ExtraBold", 0, 64)); // NOI18N
        lblBemVindo.setForeground(new java.awt.Color(51, 51, 51));
        lblBemVindo.setText("Administrador");
        lblBemVindo.setBounds(300, 250, 579, 60);
        getContentPane().add(lblBemVindo);

        lblDevolver.setFont(new java.awt.Font("Exo SemiBold", 0, 18)); // NOI18N
        lblDevolver.setForeground(new java.awt.Color(51, 51, 51));
        lblDevolver.setText("Para devolver");
        lblDevolver.setBounds(310, 350, 230, 50);
        getContentPane().add(lblDevolver);

        btnLogout.setBackground(new java.awt.Color(33, 64, 140));
        btnLogout.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Sair");
        btnLogout.setBorderPainted(false);
        btnLogout.setBounds(50, 500, 200, 40);
        getContentPane().add(btnLogout);

        btnEditarPerfil.setBackground(new java.awt.Color(33, 64, 140));
        btnEditarPerfil.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnEditarPerfil.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarPerfil.setText("Editar perfil");
        btnEditarPerfil.setBorderPainted(false);
        btnEditarPerfil.setBounds(50, 350, 200, 40);
        getContentPane().add(btnEditarPerfil);

        btnPegarLivro.setBackground(new java.awt.Color(33, 64, 140));
        btnPegarLivro.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnPegarLivro.setForeground(new java.awt.Color(255, 255, 255));
        btnPegarLivro.setText("Consultar livros");
        btnPegarLivro.setBorderPainted(false);
        btnPegarLivro.setBounds(50, 400, 200, 40);
        getContentPane().add(btnPegarLivro);

        btnAdmin.setBackground(new java.awt.Color(33, 64, 140));
        btnAdmin.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnAdmin.setForeground(new java.awt.Color(255, 255, 255));
        btnAdmin.setText("Área do Admin");
        btnAdmin.setBorderPainted(false);
        getContentPane().add(btnAdmin);

        cmbLivrosPegos.setBackground(new java.awt.Color(97, 115, 140));
        cmbLivrosPegos.setFont(new java.awt.Font("Exo Bold", 0, 11)); // NOI18N
        cmbLivrosPegos.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbLivrosPegos.setToolTipText("Livros para devolver");
        cmbLivrosPegos.setBounds(310, 390, 230, 50);
        getContentPane().add(cmbLivrosPegos);

        lblTitulo.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setText("Bem vindo(a), ");
        lblTitulo.setBounds(300, 190, 579, 50);
        getContentPane().add(lblTitulo);

        cmbLivrosEmprestados.setBackground(new java.awt.Color(97, 115, 140));
        cmbLivrosEmprestados.setFont(new java.awt.Font("Exo Bold", 0, 11)); // NOI18N
        cmbLivrosEmprestados.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbLivrosEmprestados.setToolTipText("Livros na biblioteca");
        cmbLivrosEmprestados.setBounds(570, 390, 230, 50);
        getContentPane().add(cmbLivrosEmprestados);

        lblBiblioteca.setFont(new java.awt.Font("Exo SemiBold", 0, 18)); // NOI18N
        lblBiblioteca.setForeground(new java.awt.Color(51, 51, 51));
        lblBiblioteca.setText("Na biblioteca");
        lblBiblioteca.setBounds(570, 350, 230, 50);
        getContentPane().add(lblBiblioteca);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor da pagina do usuario
     * Configura os objetos de acordo com o usuario atual
     * @param usuario usuario que entrou no sistema
     */
    public UserGUI(Usuario usuario) {
        // Configuração da janela
        super("Pagina do usuario");

        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.usuario = usuario;

        // atualiza a foto do usuario
        lblFoto.setIcon(new ImageIcon(new ImageIcon(usuario.getFoto()).getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT)));

        btnEditarPerfil.addActionListener(this);
        btnPegarLivro.addActionListener(this);

        if (usuario.isAdmin()) { // Verificação se usuário é administrador para o botao especial
            btnAdmin.setBounds(50, 450, 200, 40);
            btnAdmin.addActionListener(this);
        }

        btnLogout.addActionListener(this);

        updateComboLivrosPegos();
        updateComboLivrosEmprestados();
        updateInformacoes();

        this.setVisible(true);
    }
}
