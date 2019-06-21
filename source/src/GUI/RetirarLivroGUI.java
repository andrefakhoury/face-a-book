package GUI;

import banco_dados.ConexaoBanco;
import content.Disponibilidade;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Subclasse de JDialog - interface grafica de retirar livros que foi emprestado à biblioteca
 */
public class RetirarLivroGUI extends JDialog implements ActionListener {
    private JButton btnConfirmar;
    private JComboBox cmbDisponibilidades;
    private JLabel lblLogo, lblRetire;
    private JPanel panMain;

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnConfirmar)) {//Ação ao "Confirmar"
            Disponibilidade disponibilidade = (Disponibilidade) cmbDisponibilidades.getSelectedItem();
            if (disponibilidade == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma disponibilidade", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();
            if (conexaoBanco.removerDisponibilidade(disponibilidade)) {//retirada do livro selecionado
                JOptionPane.showMessageDialog(this, "Retirado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro na retirada", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            conexaoBanco.disconnect();
        }
    }

    /**
     * Inicializa os componentes java swing
     * Criado automaticamente pelo NetBeans
     */
    private void initComponents() {
        panMain = new JPanel();
        lblLogo = new JLabel();
        lblRetire = new JLabel();
        btnConfirmar = new JButton();
        cmbDisponibilidades = new JComboBox<>();

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

        lblRetire.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblRetire.setForeground(new java.awt.Color(51, 51, 51));
        lblRetire.setHorizontalAlignment(SwingConstants.CENTER);
        lblRetire.setText("Retire um livro");
        lblRetire.setBounds(150, 210, 590, 50);
        getContentPane().add(lblRetire);

        btnConfirmar.setBackground(new java.awt.Color(33, 64, 140));
        btnConfirmar.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setText("Retirar livro");
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setBounds(340, 450, 230, 50);
        getContentPane().add(btnConfirmar);

        cmbDisponibilidades.setBackground(new java.awt.Color(0, 51, 153));
        cmbDisponibilidades.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbDisponibilidades.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDisponibilidades.setToolTipText("Livros na biblioteca");
        cmbDisponibilidades.setBounds(240, 330, 420, 60);
        getContentPane().add(cmbDisponibilidades);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Construtor da classe de Retirada de livro
     * Configura o conteudo dos campos swing
     */
    public RetirarLivroGUI() {
        // Configuração da janela

        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModal(true);

        // atualiza o combo de disponibilidades
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Disponibilidade> disponibilidades = conexaoBanco.getDisponibilidades();
        conexaoBanco.disconnect();

        cmbDisponibilidades.removeAllItems();
        for (Disponibilidade disponibilidade : disponibilidades) {
            cmbDisponibilidades.addItem(disponibilidade);
        }

        btnConfirmar.addActionListener(this);
    }
}
