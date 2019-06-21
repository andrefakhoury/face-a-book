package GUI;

import banco_dados.ConexaoBanco;
import content.Usuario;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Subclasse de JDialog - interface grafica de edicao do perfil
 */
public class EditarPerfilGUI extends JDialog implements ActionListener {
    // objetos swing
    private JButton btnFoto, btnCancelar, btnConfirmar;
    private JLabel lblLogo, lblEditar, lblOldPass, lblNewPass;
    private JPanel panMain;
    private JTextField txtFoto;
    private JPasswordField txtNewPassword, txtOldPassword;

    // usuario passado por parametro
    private Usuario usuario;

    /**
     * Metodo que confirma a senha passada no parametro com a senha do usuario
     * @param password
     * @return
     */
    private boolean confirmaSenha(String password) {//Método de validação de senha
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        Usuario curUsuario = conexaoBanco.getUsuario(usuario.getUsername(), password);
        conexaoBanco.disconnect();

        return curUsuario != null && curUsuario.getId() == usuario.getId();
    }

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnConfirmar)) { //Ação ao "Confirmar"
            String oldPass = txtOldPassword.getText();
            String newPass = txtNewPassword.getText();
            String newFoto = txtFoto.getText();

            if (oldPass == null || oldPass.equals("") || !confirmaSenha(oldPass)) {//validação da senha antiga
                JOptionPane.showMessageDialog(this,
                        "Senha anterior incorreta!", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (newPass == null || newPass.equals("")) {
                newPass = oldPass;
            }

            usuario.setFoto(newFoto);

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();
            boolean updated = conexaoBanco.updateUsuario(usuario, newPass);
            conexaoBanco.disconnect();

            if (updated) {
                JOptionPane.showMessageDialog(this,
                        "Usuario editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Usuario nao editado", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } else if (actionEvent.getSource().equals(btnCancelar)) { //Ação ao "Cancelar"
            txtOldPassword.setText("");
            txtNewPassword.setText("");
            txtFoto.setText("");

            this.setVisible(false);
            this.dispose();
        } else if (actionEvent.getSource().equals(btnFoto)) {//Ação ao "+ Foto"
            JFileChooser fc = new JFileChooser();
            fc.resetChoosableFileFilters();
            fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));

            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                if (!file.getName().endsWith(".png") && !file.getName().endsWith(".jpg")) {//validação da extensão da imagem
                    JOptionPane.showMessageDialog(this, "Imagem invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    if (!new File("images").exists()) {
                        new File("images").mkdirs();
                    }

                    String outName = Paths.get("").toAbsolutePath().toString() + "/images/" + new Random().nextInt() + file.getName();
                    File output = new File(outName);

                    if (!Files.exists(output.toPath())) {
                        Files.copy(file.toPath(), output.toPath());
                    }

                    txtFoto.setText(outName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao selecionar a imagem\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Inicializa os componentes java swing
     * Criado automaticamente pelo NetBeans
     */
    private void initComponents() {
        panMain = new JPanel();
        lblLogo = new JLabel();
        btnCancelar = new JButton();
        txtFoto = new JTextField();
        lblEditar = new JLabel();
        txtNewPassword = new JPasswordField();
        btnFoto = new JButton();
        btnConfirmar = new JButton();
        txtOldPassword = new JPasswordField();
        lblNewPass = new JLabel();
        lblOldPass = new JLabel();

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

        btnCancelar.setBackground(new java.awt.Color(33, 64, 140));
        btnCancelar.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorderPainted(false);
        btnCancelar.setBounds(480, 560, 180, 50);
        getContentPane().add(btnCancelar);

        txtFoto.setEditable(false);
        txtFoto.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtFoto.setText("Escolha nova foto");
        txtFoto.setBounds(240, 480, 360, 50);
        getContentPane().add(txtFoto);

        lblEditar.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblEditar.setForeground(new java.awt.Color(51, 51, 51));
        lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditar.setText("Editar perfil");
        lblEditar.setBounds(150, 210, 590, 50);
        getContentPane().add(lblEditar);

        lblNewPass.setFont(new java.awt.Font("Exo ExtraBold", 0, 14));
        lblNewPass.setText("Senha nova (deixe em branco para não alterar)");
        lblNewPass.setBounds(240, 370, 420, 50);
        getContentPane().add(lblNewPass);

        txtNewPassword.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtNewPassword.setText("");
        txtNewPassword.setToolTipText("");
        txtNewPassword.setBounds(240, 405, 420, 50);
        getContentPane().add(txtNewPassword);

        btnFoto.setBackground(new java.awt.Color(33, 64, 140));
        btnFoto.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnFoto.setForeground(new java.awt.Color(255, 255, 255));
        btnFoto.setIcon(new ImageIcon(Paths.get(".").toAbsolutePath() + "/images/photoIcon.png")); // NOI18N
        btnFoto.setToolTipText("");
        btnFoto.setBorderPainted(false);
        btnFoto.setBounds(610, 480, 50, 50);
        getContentPane().add(btnFoto);

        btnConfirmar.setBackground(new java.awt.Color(33, 64, 140));
        btnConfirmar.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setBounds(240, 560, 180, 50);
        getContentPane().add(btnConfirmar);

        lblOldPass.setFont(new java.awt.Font("Exo ExtraBold", 0, 14));
        lblOldPass.setText("Senha anterior");
        lblOldPass.setBounds(240, 300, 420, 50);
        getContentPane().add(lblOldPass);

        txtOldPassword.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtOldPassword.setText("");
        txtOldPassword.setToolTipText("");
        txtOldPassword.setBounds(240, 335, 420, 50);
        getContentPane().add(txtOldPassword);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor da classe
     * Configura os parametros iniciais
     * @param usuario Usuario a ter o perfil editado
     */
    public EditarPerfilGUI(Usuario usuario) {
        // Configuração da janela
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.usuario = usuario;
        this.setModal(true);

        // atualiza os parametros especiais
        txtFoto.setText(usuario.getFoto());
        btnFoto.addActionListener(this);
        btnConfirmar.addActionListener(this);
        btnCancelar.addActionListener(this);

        this.setVisible(true);
    }
}
