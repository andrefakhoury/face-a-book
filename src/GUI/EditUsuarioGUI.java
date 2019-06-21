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

public class EditUsuarioGUI extends JDialog implements ActionListener {

    private JComboBox cmbUsuarios;

    private JButton btnUserEditar, btnUserAdd, btnUserClear, btnUserCancel;
    private JTextField txtUserId, txtUserNome, txtUserUsername, txtUserPassword, txtUserFoto;
    private JButton btnFoto;
    private JCheckBox ckbUserAdmin;

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnUserEditar)) {
            if (cmbUsuarios.getSelectedIndex() == 0) {
                fillItems(null);
            } else {
                fillItems((Usuario) cmbUsuarios.getSelectedItem());
            }
        } else if (actionEvent.getSource().equals(btnUserClear)) {
            fillItems(null);
        } else if (actionEvent.getSource().equals(btnUserCancel)) {
            fillItems(null);
            habilitaTexto(false);
        } else if (actionEvent.getSource().equals(btnUserAdd)) {

            if (txtUserUsername.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Nome de usuario invalido!", "Falha", JOptionPane.WARNING_MESSAGE);
            } else {
                String user = txtUserUsername.getText();
                String usId = txtUserId.getText();
                String foto = txtUserFoto.getText();
                String nome = txtUserNome.getText();
                String pass = txtUserPassword.getText();

                if (user == null || user.equals("")) {
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

                if (cmbUsuarios.getSelectedIndex() == 0) {
                    if (conexaoBanco.getUsuario(usuario.getUsername(), null) != null) {
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

                if (ok) {
                    JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (actionEvent.getSource().equals(btnFoto)) {
            JFileChooser fc = new JFileChooser();
            fc.resetChoosableFileFilters();
            fc.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));

            int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                if (!file.getName().endsWith(".png") && !file.getName().endsWith(".jpg")) {
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

                    txtUserFoto.setText(outName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao selecionar a imagem\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public EditUsuarioGUI() {
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        JPanel panMain = new JPanel(null);

        cmbUsuarios = new JComboBox();
        cmbUsuarios.setBounds(10, 10, 200, 20);
        panMain.add(cmbUsuarios);

        btnUserEditar = new JButton("Editar");
        btnUserEditar.setBounds(210, 10, 100, 20);
        btnUserEditar.addActionListener(this);
        panMain.add(btnUserEditar);

        txtUserId = new JTextField();
        txtUserId.setToolTipText("Id");
        txtUserId.setBounds(10, 30, 100, 20);
        panMain.add(txtUserId);

        txtUserNome = new JTextField();
        txtUserNome.setToolTipText("Nome");
        txtUserNome.setBounds(10, 50, 100, 20);
        panMain.add(txtUserNome);

        txtUserUsername = new JTextField();
        txtUserUsername.setToolTipText("Username");
        txtUserUsername.setBounds(10, 70, 100, 20);
        panMain.add(txtUserUsername);

        txtUserPassword = new JTextField();
        txtUserPassword.setToolTipText("Password");
        txtUserPassword.setBounds(10, 90, 100, 20);
        panMain.add(txtUserPassword);

        txtUserFoto = new JTextField();
        txtUserFoto.setToolTipText("Foto");
        txtUserFoto.setBounds(10, 110, 100, 20);
        panMain.add(txtUserFoto);

        btnFoto = new JButton("+ Foto");
        btnFoto.addActionListener(this);
        btnFoto.setBounds(110, 110, 100, 20);
        panMain.add(btnFoto);

        ckbUserAdmin = new JCheckBox("Admin");
        ckbUserAdmin.setBounds(10, 130, 100, 20);
        panMain.add(ckbUserAdmin);

        btnUserAdd = new JButton("Confirmar");
        btnUserAdd.addActionListener(this);
        btnUserAdd.setBounds(10, 150, 200, 20);
        panMain.add(btnUserAdd);

        btnUserClear = new JButton("Limpar");
        btnUserClear.addActionListener(this);
        btnUserClear.setBounds(10, 170, 200, 20);
        panMain.add(btnUserClear);

        btnUserCancel = new JButton("Cancelar");
        btnUserCancel.addActionListener(this);
        btnUserCancel.setBounds(10, 190, 200, 20);
        panMain.add(btnUserCancel);

        habilitaTexto(false);
        updateComboUsuarios();

        this.add(panMain);
        this.setVisible(true);
    }
}
