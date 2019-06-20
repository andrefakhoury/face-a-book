package GUI;

import banco_dados.ConexaoBanco;
import content.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class EditUsuarioGUI extends JDialog implements ActionListener {

    private JComboBox cmbUsuarios;

    private JButton btnUserEditar, btnUserAdd, btnUserClear, btnUserCancel;
    private JTextField txtUserId, txtUserNome, txtUserUsername, txtUserPassword, txtUserFoto;
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
