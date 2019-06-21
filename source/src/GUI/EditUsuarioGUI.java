package GUI;

import banco_dados.ConexaoBanco;
import content.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * Subclasse de JDialog - interface grafica de edicao de usuarios
 */
public class EditUsuarioGUI extends JDialog implements ActionListener {
    // objetos swing
    private JPanel panMain;
    private JLabel lblLogo, lblEditar;
    private JButton btnFoto, btnUserAdd, btnUserCancel, btnUserClear, btnUserEditar;
    private JCheckBox ckbUserAdmin;
    private JComboBox cmbUsuarios;
    private JTextField txtUserId, txtUserNome, txtUserPassword, txtUserUsername, txtUserFoto;

    /**
     * Habilita ou desabilita os objetos
     * @param enable Booleano de habilitacao/desabilitacao (true/false)
     */
    private void habilitaTexto(boolean enable) {
        cmbUsuarios.setEnabled(!enable);
        btnUserEditar.setEnabled(!enable);

        txtUserId.setEnabled(false);
        txtUserNome.setEnabled(enable);
        txtUserUsername.setEnabled(enable);
        txtUserPassword.setEnabled(enable);
        txtUserFoto.setEnabled(enable);
        ckbUserAdmin.setEnabled(enable);
        btnFoto.setEnabled(enable);

        btnUserAdd.setEnabled(enable);
        btnUserClear.setEnabled(enable);
        btnUserCancel.setEnabled(enable);
    }

    // atualiza o combo box de usuarios
    public void updateComboUsuarios() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Usuario> usuarios = conexaoBanco.getUsuarios();
        conexaoBanco.disconnect();
        cmbUsuarios.removeAllItems();
        cmbUsuarios.addItem("-- Adicionar --");
        for (Usuario usuario : usuarios) {
            cmbUsuarios.addItem(usuario);
        }
    }

    /**
     * Preenche os campos da tela com o usuario enviada por parametro
     * @param usuario usuario a preencher
     */
    private void fillItems(Usuario usuario) {
        habilitaTexto(true);

        if (usuario == null) {
            txtUserId.setText("");
            txtUserNome.setText("");
            txtUserUsername.setText("");
            txtUserPassword.setText("");
            ckbUserAdmin.setSelected(false);
            txtUserFoto.setText("");
        } else {
            txtUserId.setText(usuario.getId() + "");
            txtUserNome.setText(usuario.getNome());
            txtUserUsername.setText(usuario.getUsername());
            txtUserPassword.setText("");
            txtUserPassword.setEnabled(false);
            ckbUserAdmin.setSelected(usuario.isAdmin());
            txtUserFoto.setText(usuario.getFoto());
        }
    }

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnUserEditar)) {//Ação ao "Editar"
            if (cmbUsuarios.getSelectedIndex() == 0) {
                fillItems(null);
            } else {
                fillItems((Usuario) cmbUsuarios.getSelectedItem());
            }
        } else if (actionEvent.getSource().equals(btnUserClear)) {//Ação ao "Limpar"
            fillItems(null);
        } else if (actionEvent.getSource().equals(btnUserCancel)) {//Ação ao "Cancelar"
            fillItems(null);
            habilitaTexto(false);
        } else if (actionEvent.getSource().equals(btnUserAdd)) {//Ação ao "Confirmar"

            if (txtUserUsername.getText().equals("")) {//validação do campo
                JOptionPane.showMessageDialog(this, "Nome de usuario invalido!", "Falha", JOptionPane.WARNING_MESSAGE);
            } else {
                String user = txtUserUsername.getText();
                String usId = txtUserId.getText();
                String foto = txtUserFoto.getText();
                String nome = txtUserNome.getText();
                String pass = txtUserPassword.getText();

                if (user == null || user.equals("")) {//validação do campo
                    JOptionPane.showMessageDialog(this, "Nome de usuario invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usId == null || usId.equals("")) {
                    usId = "0";
                }

                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(usId));
                usuario.setNome(nome);
                usuario.setFoto(foto);
                usuario.setAdmin(ckbUserAdmin.isSelected());
                usuario.setUsername(user);

                ConexaoBanco conexaoBanco = new ConexaoBanco();
                conexaoBanco.connect();

                boolean ok = false;
                String message = "";

                if (cmbUsuarios.getSelectedIndex() == 0) {//confirmação de inserção/edição
                    if (conexaoBanco.getUsuario(usuario.getUsername(), null) != null) {//Verificação de existência do usuário
                        JOptionPane.showMessageDialog(this, "Usuario ja cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                        conexaoBanco.disconnect();
                        return;
                    }

                    ok = conexaoBanco.insertUsuario(usuario, pass);
                    message = "Inserido com sucesso!";
                } else {
                    ok = conexaoBanco.updateUsuario(usuario, null);
                    message = "Editado com sucesso!";
                }

                conexaoBanco.disconnect();

                if (ok) {//Confirmação da operação
                    JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (actionEvent.getSource().equals(btnFoto)) {//Ação ao "+ Foto"
            JFileChooser fc = new JFileChooser();
            fc.resetChoosableFileFilters();
            fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));

            // pega a foto desejada
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                if (!file.getName().endsWith(".png") && !file.getName().endsWith(".jpg")) {
                    JOptionPane.showMessageDialog(this, "Imagem invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    if (!new File("images").exists()) { // se o diretorio nao existir, cria
                        new File("images").mkdirs();
                    }

                    // copia o caminho do arquivo a ser salvo
                    String outName = Paths.get("").toAbsolutePath().toString() + "/images/" + new Random().nextInt() + file.getName();
                    File output = new File(outName);

                    // tenta salvar, se o arquivo ainda nao existir
                    if (!Files.exists(output.toPath())) {
                        Files.copy(file.toPath(), output.toPath());
                    }

                    txtUserFoto.setText(outName);
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
        btnUserCancel = new JButton();
        btnUserEditar = new JButton();
        txtUserFoto = new JTextField();
        lblEditar = new JLabel();
        cmbUsuarios = new JComboBox<>();
        txtUserUsername = new JTextField();
        btnFoto = new JButton();
        btnUserAdd = new JButton();
        btnUserClear = new JButton();
        txtUserId = new JTextField();
        txtUserNome = new JTextField();
        ckbUserAdmin = new JCheckBox();
        txtUserPassword = new JTextField();

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

        btnUserCancel.setBackground(new java.awt.Color(33, 64, 140));
        btnUserCancel.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnUserCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnUserCancel.setText("Cancelar");
        btnUserCancel.setBorderPainted(false);
        btnUserCancel.setBounds(540, 550, 180, 50);
        getContentPane().add(btnUserCancel);

        btnUserEditar.setBackground(new java.awt.Color(33, 64, 140));
        btnUserEditar.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnUserEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnUserEditar.setText("Editar");
        btnUserEditar.setToolTipText("");
        btnUserEditar.setBorderPainted(false);
        btnUserEditar.setBounds(580, 270, 80, 30);
        getContentPane().add(btnUserEditar);

        txtUserFoto.setEditable(false);
        txtUserFoto.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtUserFoto.setText("Escolha nova foto");
        txtUserFoto.setBounds(240, 470, 360, 40);
        getContentPane().add(txtUserFoto);

        lblEditar.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblEditar.setForeground(new java.awt.Color(51, 51, 51));
        lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditar.setText("Editar usuario");
        lblEditar.setBounds(150, 190, 590, 50);
        getContentPane().add(lblEditar);

        cmbUsuarios.setBackground(new java.awt.Color(0, 51, 153));
        cmbUsuarios.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbUsuarios.setToolTipText("Livros na biblioteca");
        cmbUsuarios.setBounds(240, 270, 330, 30);
        getContentPane().add(cmbUsuarios);

        txtUserUsername.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtUserUsername.setText("Username do usuario");
        txtUserUsername.setToolTipText("");
        txtUserUsername.setBounds(240, 390, 420, 30);
        getContentPane().add(txtUserUsername);

        btnFoto.setBackground(new java.awt.Color(33, 64, 140));
        btnFoto.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnFoto.setForeground(new java.awt.Color(255, 255, 255));
        btnFoto.setIcon(new ImageIcon(Paths.get(".").toAbsolutePath() + "/images/photoIcon.png")); // NOI18N
        btnFoto.setToolTipText("");
        btnFoto.setBorderPainted(false);
        btnFoto.setBounds(610, 470, 50, 40);
        getContentPane().add(btnFoto);

        btnUserAdd.setBackground(new java.awt.Color(33, 64, 140));
        btnUserAdd.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnUserAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnUserAdd.setText("Confirmar");
        btnUserAdd.setBorderPainted(false);
        btnUserAdd.setBounds(160, 550, 180, 50);
        getContentPane().add(btnUserAdd);

        btnUserClear.setBackground(new java.awt.Color(33, 64, 140));
        btnUserClear.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnUserClear.setForeground(new java.awt.Color(255, 255, 255));
        btnUserClear.setText("Limpar");
        btnUserClear.setBorderPainted(false);
        btnUserClear.setBounds(350, 550, 180, 50);
        getContentPane().add(btnUserClear);

        txtUserId.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtUserId.setText("ID do usuario");
        txtUserId.setToolTipText("");
        txtUserId.setBounds(240, 310, 420, 30);
        getContentPane().add(txtUserId);

        txtUserNome.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtUserNome.setText("Nome do usuario");
        txtUserNome.setToolTipText("");
        txtUserNome.setBounds(240, 350, 420, 30);
        getContentPane().add(txtUserNome);

        ckbUserAdmin.setFont(new java.awt.Font("Exo SemiBold", 2, 14)); // NOI18N
        ckbUserAdmin.setForeground(new java.awt.Color(0, 51, 102));
        ckbUserAdmin.setText("Admin");
        ckbUserAdmin.setBounds(400, 510, 300, 30);
        getContentPane().add(ckbUserAdmin);

        txtUserPassword.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtUserPassword.setText("Senha do usuario");
        txtUserPassword.setToolTipText("");
        txtUserPassword.setBounds(240, 430, 420, 30);
        getContentPane().add(txtUserPassword);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor da classe
     * Prepara as configuracoes iniciais
     */
    public EditUsuarioGUI() {
        // Configuração da janela
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        btnUserEditar.addActionListener(this);
        btnFoto.addActionListener(this);
        btnUserAdd.addActionListener(this);
        btnUserClear.addActionListener(this);
        btnUserCancel.addActionListener(this);

        // atualiza as informacoes
        habilitaTexto(false);
        updateComboUsuarios();

        this.setVisible(true);
    }
}
