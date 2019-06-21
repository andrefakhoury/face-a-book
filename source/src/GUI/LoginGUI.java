package GUI;

import banco_dados.ConexaoBanco;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import content.*;

/**
 * Subclasse de JFrame - interface grafica da parte de login
 */
public class LoginGUI extends JFrame implements ActionListener {
    // objetos swing
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblLogo, lblSenha, lblLogo2, lblUsuario;
    private JButton btnConnect;
    private JPanel panMain;

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        
        if (actionEvent.getSource().equals(btnConnect)) {//ação efetuada ao clicar no botão de conectar

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();

            Usuario usuario = conexaoBanco.getUsuario(txtUsername.getText(), txtPassword.getText());

            if (usuario == null) {
                JOptionPane.showMessageDialog(this, "Nenhum usuario encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                this.setVisible(false);
                UserGUI userGUI = new UserGUI(usuario);
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
        lblLogo2 = new JLabel();
        txtPassword = new JPasswordField();
        btnConnect = new JButton();
        txtUsername = new JTextField();
        lblSenha = new JLabel();
        lblUsuario = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        lblLogo2.setFont(new java.awt.Font("Exo ExtraBold", 0, 48)); // NOI18N
        lblLogo2.setForeground(new java.awt.Color(33, 64, 140));
        lblLogo2.setIcon(new ImageIcon(Paths.get("").toAbsolutePath() + "/images/face-a-book.png")); // NOI18N
        lblLogo2.setBounds(150, -170, 926, 700);
        getContentPane().add(lblLogo2);

        txtPassword.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtPassword.setText("Senha");
        txtPassword.setToolTipText("");
        txtPassword.setBounds(240, 440, 420, 60);
        getContentPane().add(txtPassword);

        btnConnect.setBackground(new java.awt.Color(33, 64, 140));
        btnConnect.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnConnect.setForeground(new java.awt.Color(255, 255, 255));
        btnConnect.setText("Conectar");
        btnConnect.setBorderPainted(false);
        btnConnect.setBounds(360, 540, 180, 50);
        getContentPane().add(btnConnect);

        txtUsername.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtUsername.setText("Usuario");
        txtUsername.setToolTipText("");
        txtUsername.setBounds(240, 330, 420, 60);
        getContentPane().add(txtUsername);

        lblSenha.setFont(new java.awt.Font("Exo ExtraBold", 0, 24)); // NOI18N
        lblSenha.setForeground(new java.awt.Color(51, 51, 51));
        lblSenha.setText("Senha");
        lblSenha.setBounds(240, 400, 110, 40);
        getContentPane().add(lblSenha);

        lblUsuario.setFont(new java.awt.Font("Exo ExtraBold", 0, 24)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(51, 51, 51));
        lblUsuario.setText("Usuario");
        lblUsuario.setBounds(240, 290, 110, 40);
        getContentPane().add(lblUsuario);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor da classe
     * Atualiza os objetos utilizados
     */
    public LoginGUI() {
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        txtUsername.setText("");
        txtPassword.setText("");
        btnConnect.addActionListener(this);

        this.setVisible(true);
    }
}
