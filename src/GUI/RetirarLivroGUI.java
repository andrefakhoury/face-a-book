package GUI;

import banco_dados.ConexaoBanco;
import content.Disponibilidade;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RetirarLivroGUI extends JDialog implements ActionListener {
    private JComboBox cmbDisponibilidades;
    private JButton btnConfirmar;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnConfirmar)) {
            Disponibilidade disponibilidade = (Disponibilidade) cmbDisponibilidades.getSelectedItem();
            if (disponibilidade == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma disponibilidade", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();
            if (conexaoBanco.removerDisponibilidade(disponibilidade)) {
                JOptionPane.showMessageDialog(this, "Retirado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro na retirada", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            conexaoBanco.disconnect();
        }
    }

    public RetirarLivroGUI() {
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        JPanel panMain = new JPanel(null);

        cmbDisponibilidades = new JComboBox();
        cmbDisponibilidades.setBounds(10, 10, 200, 50);
        panMain.add(cmbDisponibilidades);

        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Disponibilidade> disponibilidades = conexaoBanco.getDisponibilidades();
        conexaoBanco.disconnect();

        for (Disponibilidade disponibilidade : disponibilidades) {
            cmbDisponibilidades.addItem(disponibilidade);
        }

        btnConfirmar = new JButton("Confirma devolucao");
        btnConfirmar.setBounds(20, 65, 200, 50);
        panMain.add(btnConfirmar);
        btnConfirmar.addActionListener(this);

        this.add(panMain);
        this.setVisible(true);
    }
}
