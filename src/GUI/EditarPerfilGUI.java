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

public class EditarPerfilGUI extends JDialog implements ActionListener {
    private JTextField txtOldPassword, txtNewPassword, txtFoto;
    private JButton btnConfirmar, btnCancelar, btnFoto;
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

                    txtFoto.setText(outName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao selecionar a imagem\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public EditarPerfilGUI(Usuario usuario) {
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

        btnFoto = new JButton("+ Foto");
        btnFoto.addActionListener(this);
        panMain.add(btnFoto);

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
