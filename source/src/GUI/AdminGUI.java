package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.nio.file.Paths;

/**
 * Subclasse de JFrame - interface grafica do administrador
 */
public class AdminGUI extends JFrame implements ActionListener {
    // Elementos swing
    private JButton btnConfirmaDevolucao, btnConfirmaEmprestimo, btnEditCategoria;
    private JButton btnEditEmprestimo, btnEditLivro, btnEditUsuario, btnPegarLivroDeVolta;
    private JLabel lblLogo, lblAreaAdm;
    private JPanel panMain;

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnEditLivro)) {//Ação ao "Editar livro"
            new EditLivroGUI();
        } else if (actionEvent.getSource().equals(btnEditCategoria)) {//Ação ao "Editar categoria"
            new EditCategoriaGUI();
        } else if (actionEvent.getSource().equals(btnEditUsuario)) {//Ação ao "Editar usuário"
            new EditUsuarioGUI();
        } else if (actionEvent.getSource().equals(btnEditEmprestimo)) {//Ação ao "Editar empréstimo"
            new EditEmprestimoGUI();
        } else if (actionEvent.getSource().equals(btnConfirmaDevolucao)) {//Ação ao "Confirmar devolução"
            new DevolucaoGUI();
        } else if (actionEvent.getSource().equals(btnConfirmaEmprestimo)) {//Ação ao "Confirmar empréstimo"
            new ConfirmaEmprestimoGUI();
        } else if (actionEvent.getSource().equals(btnPegarLivroDeVolta)) {//Ação ao "Devolver livro que emprestaram a biblioteca"
            new RetirarLivroGUI();
        }
    }

    /**
     * Inicializa os componentes java swing
     * Criado automaticamente pelo NetBeans
     */
    private void initComponents() {
        panMain = new JPanel();
        lblLogo = new JLabel();
        btnPegarLivroDeVolta = new JButton();
        lblAreaAdm = new JLabel();
        btnEditUsuario = new JButton();
        btnEditLivro = new JButton();
        btnEditEmprestimo = new JButton();
        btnEditCategoria = new JButton();
        btnConfirmaEmprestimo = new JButton();
        btnConfirmaDevolucao = new JButton();

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

        btnPegarLivroDeVolta.setBackground(new java.awt.Color(33, 64, 140));
        btnPegarLivroDeVolta.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnPegarLivroDeVolta.setForeground(new java.awt.Color(255, 255, 255));
        btnPegarLivroDeVolta.setText("Pegar livro de volta");
        btnPegarLivroDeVolta.setBorderPainted(false);
        btnPegarLivroDeVolta.setBounds(310, 560, 270, 40);
        getContentPane().add(btnPegarLivroDeVolta);

        lblAreaAdm.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblAreaAdm.setForeground(new java.awt.Color(51, 51, 51));
        lblAreaAdm.setHorizontalAlignment(SwingConstants.CENTER);
        lblAreaAdm.setText("Área do Administrador");
        lblAreaAdm.setBounds(150, 180, 590, 50);
        getContentPane().add(lblAreaAdm);

        btnEditUsuario.setBackground(new java.awt.Color(33, 64, 140));
        btnEditUsuario.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnEditUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEditUsuario.setText("Editar usuario");
        btnEditUsuario.setBorderPainted(false);
        btnEditUsuario.setBounds(310, 260, 270, 40);
        getContentPane().add(btnEditUsuario);

        btnEditLivro.setBackground(new java.awt.Color(33, 64, 140));
        btnEditLivro.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnEditLivro.setForeground(new java.awt.Color(255, 255, 255));
        btnEditLivro.setText("Editar livro");
        btnEditLivro.setBorderPainted(false);
        btnEditLivro.setBounds(310, 310, 270, 40);
        getContentPane().add(btnEditLivro);

        btnEditEmprestimo.setBackground(new java.awt.Color(33, 64, 140));
        btnEditEmprestimo.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnEditEmprestimo.setForeground(new java.awt.Color(255, 255, 255));
        btnEditEmprestimo.setText("Editar emprestimo");
        btnEditEmprestimo.setBorderPainted(false);
        btnEditEmprestimo.setBounds(310, 360, 270, 40);
        getContentPane().add(btnEditEmprestimo);

        btnEditCategoria.setBackground(new java.awt.Color(33, 64, 140));
        btnEditCategoria.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnEditCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnEditCategoria.setText("Editar categoria");
        btnEditCategoria.setBorderPainted(false);
        btnEditCategoria.setBounds(310, 410, 270, 40);
        getContentPane().add(btnEditCategoria);

        btnConfirmaEmprestimo.setBackground(new java.awt.Color(33, 64, 140));
        btnConfirmaEmprestimo.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnConfirmaEmprestimo.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmaEmprestimo.setText("Confirmar emprestimo");
        btnConfirmaEmprestimo.setBorderPainted(false);
        btnConfirmaEmprestimo.setBounds(310, 460, 270, 40);
        getContentPane().add(btnConfirmaEmprestimo);

        btnConfirmaDevolucao.setBackground(new java.awt.Color(33, 64, 140));
        btnConfirmaDevolucao.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnConfirmaDevolucao.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmaDevolucao.setText("Confirmar devolucao");
        btnConfirmaDevolucao.setToolTipText("");
        btnConfirmaDevolucao.setBorderPainted(false);
        btnConfirmaDevolucao.setBounds(310, 510, 270, 40);
        getContentPane().add(btnConfirmaDevolucao);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor da classe
     */
    public AdminGUI() {
        initComponents();

        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // adiciona os action listener dos botoes
        btnEditUsuario.addActionListener(this);
        btnEditLivro.addActionListener(this);
        btnEditEmprestimo.addActionListener(this);
        btnEditCategoria.addActionListener(this);
        btnConfirmaEmprestimo.addActionListener(this);
        btnConfirmaDevolucao.addActionListener(this);
        btnPegarLivroDeVolta.addActionListener(this);

        this.setVisible(true);
    }
}
