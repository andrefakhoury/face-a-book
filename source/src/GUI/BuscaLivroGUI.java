package GUI;

import banco_dados.ConexaoBanco;
import content.Disponibilidade;
import content.Livro;
import content.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Subclasse de JDialog - interface grafica da busca de livros
 */
public class BuscaLivroGUI extends JDialog implements ActionListener {
    private Usuario usuario;
    private JLabel lblLogo, lblBusque;
    private JButton btnBuscar, btnReservar;
    private JComboBox cmbLivros;
    private JTextField txtBusca;
    private JPanel panMain;

    /**
     * Atualiza o comboBox de livros
     */
    private void updateComboLivros() {
        //Método para buscar os livros
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Livro> livros = conexaoBanco.getLivros(txtBusca.getText()); //todos os livros encontrados
        conexaoBanco.disconnect();

        cmbLivros.removeAllItems(); //remoção dos livros encontrados em buscas anteriores
        for (Livro livro : livros) { //exibição dos livros encontrados
            cmbLivros.addItem(livro);
        }
    }

    /**
     * Reserva um livro selecionado no comboBox
     */
    private void reservarLivro() {
        //Método para reservar livro selecionado
        Livro livro = (Livro) cmbLivros.getSelectedItem();
        if (livro == null) { //validação do livro
            JOptionPane.showMessageDialog(this, "Selecione um livro valido", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Disponibilidade> disponibilidades = conexaoBanco.getDisponibilidades(livro);

        if (disponibilidades.isEmpty()) { //verificação de disṕonibilidade do livro
            JOptionPane.showMessageDialog(this, "Livro nao disponivel!", "Erro", JOptionPane.ERROR_MESSAGE);
            conexaoBanco.disconnect();
            return;
        }

        // pega a primeira disponibiliade possivel
        Disponibilidade disponibilidade = disponibilidades.get(0);

        // atualiza o formato da data
        java.util.Calendar cal1 = Calendar.getInstance();
        cal1.setTime(disponibilidade.getData());

        java.util.Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new java.util.Date());
        cal2.add(Calendar.DATE, 7);

        java.util.Calendar cal;
        if (cal1.before(cal2)) {
            cal = cal1;
        } else {
            cal = cal2;
        }

        java.sql.Date dataEntrega = new java.sql.Date(cal.getTime().getTime());

        if (conexaoBanco.emprestarLivro(disponibilidade, usuario, dataEntrega)) { //Exibição de confirmação do empréstimo
            JOptionPane.showMessageDialog(this, "Livro emprestado!\nPasse na biblioteca para pegar seu livro :)", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro no emprestimo!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        conexaoBanco.disconnect();
    }

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnBuscar)) {//Ação ao "Buscar"
            updateComboLivros();
        } else if (actionEvent.getSource().equals(btnReservar)) {//Ação ao "Reservar"
            reservarLivro();
        }
    }

    /**
     * Inicializa os componentes java swing
     * Criado automaticamente pelo NetBeans
     */
    private void initComponents() {
        panMain = new JPanel();
        lblLogo = new JLabel();
        btnReservar = new JButton();
        btnBuscar = new JButton();
        txtBusca = new JTextField();
        lblBusque = new JLabel();
        cmbLivros = new JComboBox<>();

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

        btnReservar.setBackground(new java.awt.Color(33, 64, 140));
        btnReservar.setFont(new java.awt.Font("Exo Black", 0, 36)); // NOI18N
        btnReservar.setForeground(new java.awt.Color(255, 255, 255));
        btnReservar.setText("Reservar");
        btnReservar.setBorderPainted(false);
        btnReservar.setBounds(310, 530, 270, 60);
        getContentPane().add(btnReservar);

        btnBuscar.setBackground(new java.awt.Color(33, 64, 140));
        btnBuscar.setFont(new java.awt.Font("Exo Black", 0, 18)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new ImageIcon(Paths.get(".").toAbsolutePath() + "/images/searchIcon.png")); // NOI18N
        btnBuscar.setToolTipText("");
        btnBuscar.setBorderPainted(false);
        btnBuscar.setBounds(715, 330, 50, 50);
        getContentPane().add(btnBuscar);

        txtBusca.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtBusca.setText("Nome do livro");
        txtBusca.setBounds(110, 330, 595, 50);
        getContentPane().add(txtBusca);

        lblBusque.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblBusque.setForeground(new java.awt.Color(51, 51, 51));
        lblBusque.setHorizontalAlignment(SwingConstants.CENTER);
        lblBusque.setText("Busque um livro");
        lblBusque.setBounds(150, 210, 590, 50);
        getContentPane().add(lblBusque);

        cmbLivros.setBackground(new java.awt.Color(0, 51, 153));
        cmbLivros.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbLivros.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbLivros.setToolTipText("Livros na biblioteca");
        cmbLivros.setBounds(110, 390, 660, 50);
        getContentPane().add(cmbLivros);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor do buscador de livros
     * @param usuario usuario que procura os livros
     */
    public BuscaLivroGUI(Usuario usuario) {
        // inicializa os objetos do swing
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.usuario = usuario;
        this.setModal(true);
        
        // instanciação e inserção dos itens da janela
        btnBuscar.addActionListener(this);
        updateComboLivros();
        btnReservar.addActionListener(this);
        this.setVisible(true);
    }
}
