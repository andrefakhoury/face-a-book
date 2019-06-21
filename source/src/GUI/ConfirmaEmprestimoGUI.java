package GUI;

import banco_dados.ConexaoBanco;
import content.Emprestimo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Subclasse de JDialog - interface grafica de confirmar um emprestimo
 */
public class ConfirmaEmprestimoGUI extends JDialog implements ActionListener {
    // objetos java swing
    private JComboBox cmbEmprestimos;
    private JButton btnConfirmar;
    private JPanel panMain;
    private JLabel lblLogo, lblRetire;

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnConfirmar)) { //ação ao "Confirma empréstimo"
            Emprestimo emprestimo = (Emprestimo) cmbEmprestimos.getSelectedItem();
            if (emprestimo == null) {//validação do emprestimo
                JOptionPane.showMessageDialog(this, "Selecione um emprestimo", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();
            if (conexaoBanco.confirmarEmprestimo(emprestimo)) {
                JOptionPane.showMessageDialog(this, "Emprestado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro na devolucao", "ERRO", JOptionPane.ERROR_MESSAGE);
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
        cmbEmprestimos = new JComboBox<>();

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

        lblRetire.setFont(new java.awt.Font("Exo ExtraBold", 0, 46));
        lblRetire.setForeground(new java.awt.Color(51, 51, 51));
        lblRetire.setHorizontalAlignment(SwingConstants.CENTER);
        lblRetire.setText("Escolha um empréstimo");
        lblRetire.setBounds(150, 210, 590, 50);
        getContentPane().add(lblRetire);

        btnConfirmar.setBackground(new java.awt.Color(33, 64, 140));
        btnConfirmar.setFont(new java.awt.Font("Exo Black", 0, 28));
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setText("Confirmar empréstimo");
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setBounds(240, 450, 430, 50);
        getContentPane().add(btnConfirmar);

        cmbEmprestimos.setBackground(new java.awt.Color(0, 51, 153));
        cmbEmprestimos.setFont(new java.awt.Font("Exo", 0, 11));
        cmbEmprestimos.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEmprestimos.setToolTipText("Emprestimos");
        cmbEmprestimos.setBounds(240, 330, 420, 60);
        getContentPane().add(cmbEmprestimos);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Construtor da classe
     */
    public ConfirmaEmprestimoGUI() {
        // Configuração da janela
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        // reseta os itens do combo de emprestimos
        cmbEmprestimos.removeAllItems();
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Emprestimo> emprestimos = conexaoBanco.getEmprestimos();
        conexaoBanco.disconnect();

        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getStatus() == 0) {
                cmbEmprestimos.addItem(emprestimo);
            }
        }

        btnConfirmar.addActionListener(this);

        this.setVisible(true);
    }
}
