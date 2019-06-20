package GUI;

import banco_dados.ConexaoBanco;
import content.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class EditEmprestimoGUI extends JDialog implements ActionListener {
    private JComboBox cmbEmprestimos;
    private JButton btnEmprestimoEditar;

    private JButton btnEmprestimoAdd, btnEmprestimoClear, btnEmprestimoCancel;
    private JTextField txtEmprestimoId, txtEmprestimoData;
    private JComboBox cmbEmprestimoLivro, cmbEmprestimoUsuario;
    private JButton btnEmprestimoLivro, btnEmprestimoUsuario;

    private void habilitaTexto(boolean enable) {
        cmbEmprestimos.setEnabled(!enable);
        btnEmprestimoEditar.setEnabled(!enable);

        txtEmprestimoId.setEnabled(false);
        txtEmprestimoData.setEnabled(enable);

        cmbEmprestimoLivro.setEnabled(enable);
        btnEmprestimoLivro.setEnabled(enable);

        cmbEmprestimoUsuario.setEnabled(cmbEmprestimos.getSelectedIndex() == 0);
        btnEmprestimoUsuario.setEnabled(cmbEmprestimos.getSelectedIndex() == 0);

        btnEmprestimoAdd.setEnabled(enable);
        btnEmprestimoClear.setEnabled(enable);
        btnEmprestimoCancel.setEnabled(enable);
    }

    private void updateComboEmprestimos() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Disponibilidade> disponibilidades = conexaoBanco.getDisponibilidades();
        conexaoBanco.disconnect();
        cmbEmprestimos.removeAllItems();
        cmbEmprestimos.addItem("-- Adicionar --");
        for (Disponibilidade disponibilidade : disponibilidades) {
            cmbEmprestimos.addItem(disponibilidade);
        }
    }

    private void updateComboLivros() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Livro> livros = conexaoBanco.getLivros();
        conexaoBanco.disconnect();
        cmbEmprestimoLivro.removeAllItems();
        for (Livro livro : livros) {
            cmbEmprestimoLivro.addItem(livro);
        }
    }

    private void updateComboUsuarios() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Usuario> usuarios = conexaoBanco.getUsuarios();
        conexaoBanco.disconnect();
        cmbEmprestimoUsuario.removeAllItems();
        for (Usuario usuario : usuarios) {
            cmbEmprestimoUsuario.addItem(usuario);
        }
    }

    private void fillItems(Disponibilidade disponibilidade) {
        if (disponibilidade == null) {
            txtEmprestimoId.setText("");
            txtEmprestimoData.setText("01/01/2010");
            cmbEmprestimoLivro.setSelectedIndex(-1);
            cmbEmprestimoUsuario.setSelectedIndex(-1);
        } else {
            txtEmprestimoId.setText(disponibilidade.getId() + "");

            String data = disponibilidade.getData().toString();


            String novaData;

            try {
                novaData = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)).format
                        (DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }


            txtEmprestimoData.setText(novaData);

            try {
                for (int i = 0; i < cmbEmprestimoLivro.getItemCount(); i++) {
                    if (((Livro) cmbEmprestimoLivro.getItemAt(i)).getId() == disponibilidade.getLivro().getId()) {
                        cmbEmprestimoLivro.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (Exception ex) {
                cmbEmprestimoLivro.setSelectedIndex(-1);
            }

            cmbEmprestimoUsuario.setSelectedIndex(-1);
        }

        habilitaTexto(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnEmprestimoEditar)) {
            if (cmbEmprestimos.getSelectedIndex() == 0) {
                fillItems(null);
            } else {
                fillItems((Disponibilidade) cmbEmprestimos.getSelectedItem());
            }
        } else if (actionEvent.getSource().equals(btnEmprestimoClear)) {
            fillItems(null);
        } else if (actionEvent.getSource().equals(btnEmprestimoCancel)) {
            fillItems(null);
            habilitaTexto(false);
        } else if (actionEvent.getSource().equals(btnEmprestimoAdd)) {

            Livro livro = (Livro) cmbEmprestimoLivro.getSelectedItem();
            Usuario usuario = null;
            String data = txtEmprestimoData.getText();
            String id = txtEmprestimoId.getText();

            if (id == null || id.equals("")) {
                id = "0";
            }

            if (livro == null || data == null || data.equals("")) {
                JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idDisp = Integer.parseInt(id);
            int idLivro = livro.getId();
            int idUsuario = 0;

            if (cmbEmprestimos.getSelectedIndex() == 0) {
                usuario = (Usuario) cmbEmprestimoUsuario.getSelectedItem();
                if (usuario == null) {
                    JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                idUsuario = usuario.getId();
            }

            String novaData;
            java.sql.Date date;

            try {
                novaData = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US)).format
                        (DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US));
                date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(novaData).getTime());

                if (date.before(new java.util.Date())) throw new Exception("Data inferior ao dia atual");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Data invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();
            boolean ok = false;

            String message = "";

            if (cmbEmprestimos.getSelectedIndex() == 0) {
                ok = conexaoBanco.insertDisponibilidade(idLivro, idUsuario, date);
                message = "Inserido";
            } else {
                ok = conexaoBanco.updateDisponibilidade(idDisp, idLivro, date);
                message = "Atualizado";
            }

            conexaoBanco.disconnect();

            if (ok) {
                JOptionPane.showMessageDialog(this, message + " com Sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                this.setVisible(false);
            }
        } else if (actionEvent.getSource().equals(btnEmprestimoLivro)) {
            new EditLivroGUI();
            updateComboLivros();
        } else if (actionEvent.getSource().equals(btnEmprestimoUsuario)) {
            new EditUsuarioGUI();
            updateComboUsuarios();
        }
    }

    public EditEmprestimoGUI() {
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        JPanel panMain = new JPanel(null);

        cmbEmprestimos = new JComboBox();
        cmbEmprestimos.setBounds(10, 10, 200, 20);
        panMain.add(cmbEmprestimos);

        btnEmprestimoEditar = new JButton("Editar");
        btnEmprestimoEditar.setBounds(210, 10, 100, 20);
        btnEmprestimoEditar.addActionListener(this);
        panMain.add(btnEmprestimoEditar);

        txtEmprestimoId = new JTextField();
        txtEmprestimoId.setToolTipText("ID");
        txtEmprestimoId.setBounds(10, 30, 200, 20);
        panMain.add(txtEmprestimoId);

        txtEmprestimoData = new JTextField();
        txtEmprestimoData.setToolTipText("Data");
        txtEmprestimoData.setBounds(10, 50, 200, 20);
        panMain.add(txtEmprestimoData);

        cmbEmprestimoLivro = new JComboBox();
        cmbEmprestimoLivro.setBounds(10, 70, 200, 20);
        panMain.add(cmbEmprestimoLivro);

        btnEmprestimoLivro = new JButton("Novo livro");
        btnEmprestimoLivro.setBounds(10, 90, 200, 20);
        btnEmprestimoLivro.addActionListener(this);
        panMain.add(btnEmprestimoLivro);

        cmbEmprestimoUsuario = new JComboBox();
        cmbEmprestimoUsuario.setBounds(10, 110, 200, 20);
        panMain.add(cmbEmprestimoUsuario);

        btnEmprestimoUsuario = new JButton("Novo usuario");
        btnEmprestimoUsuario.setBounds(10, 130, 200, 20);
        btnEmprestimoUsuario.addActionListener(this);
        panMain.add(btnEmprestimoUsuario);

        btnEmprestimoAdd = new JButton("Confirmar");
        btnEmprestimoAdd.addActionListener(this);
        btnEmprestimoAdd.setBounds(10, 150, 200, 20);
        panMain.add(btnEmprestimoAdd);

        btnEmprestimoClear = new JButton("Limpar");
        btnEmprestimoClear.addActionListener(this);
        btnEmprestimoClear.setBounds(10, 170, 200, 20);
        panMain.add(btnEmprestimoClear);

        btnEmprestimoCancel = new JButton("Cancelar");
        btnEmprestimoCancel.addActionListener(this);
        btnEmprestimoCancel.setBounds(10, 190, 200, 20);
        panMain.add(btnEmprestimoCancel);

        habilitaTexto(false);
        updateComboEmprestimos();
        updateComboLivros();
        updateComboUsuarios();

        this.add(panMain);
        this.setVisible(true);
    }
}
