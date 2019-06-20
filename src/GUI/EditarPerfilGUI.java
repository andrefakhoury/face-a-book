package GUI;

import banco_dados.ConexaoBanco;
import content.Disponibilidade;
import content.Emprestimo;
import content.Livro;
import content.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class EditarPerfilGUI extends JDialog implements ActionListener {
    private JTextField txtOldPassword, txtNewPassword, txtFoto;
    private JButton btnConfirmar, btnCancelar;
    private Usuario usuario;

    private boolean confirmaSenha(String password) {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        Usuario curUsuario = conexaoBanco.getUsuario(usuario.getUsername(), password);
        conexaoBanco.disconnect();

        return curUsuario != null && curUsuario.getId() == usuario.getId();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnConfirmar)) {
            String oldPass = txtOldPassword.getText();
            String newPass = txtNewPassword.getText();
            String newFoto = txtFoto.getText();

            if (oldPass == null || oldPass.equals("") || !confirmaSenha(oldPass)) {
                JOptionPane.showMessageDialog(this,
                        "Insira sua senha anterior para editar!", "Erro", JOptionPane.WARNING_MESSAGE);
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

        } else if (actionEvent.getSource().equals(btnCancelar)) {
            txtOldPassword.setText("");
            txtNewPassword.setText("");
            txtFoto.setText("");

            this.setVisible(false);
            this.dispose();
        }
    }

    public EditarPerfilGUI(Usuario usuario) {
//        super("Editar perfil");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.usuario = usuario;
        this.setModal(true);

        JPanel panMain = new JPanel(new GridLayout(0, 1));

        txtOldPassword = new JTextField();
        panMain.add(txtOldPassword);

        txtNewPassword = new JTextField("DEIXE EM BRANCO SE NAO QUISER EDITAR");
        panMain.add(txtNewPassword);

        txtFoto = new JTextField(usuario.getFoto());
        panMain.add(txtFoto);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(this);
        panMain.add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        panMain.add(btnCancelar);

        this.add(panMain);
        this.setVisible(true);
    }
}
