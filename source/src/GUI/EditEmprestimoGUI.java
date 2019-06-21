package GUI;

import banco_dados.ConexaoBanco;
import content.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Subclasse de JDialog - interface grafica de edicao do emprestimo
 */
public class EditEmprestimoGUI extends JDialog implements ActionListener {
    // Objetos swing
    private JButton btnEmprestimoAdd, btnEmprestimoCancel, btnEmprestimoClear;
    private JButton btnEmprestimoEditar, btnEmprestimoLivro, btnEmprestimoUsuario;
    private JComboBox cmbEmprestimoLivro, cmbEmprestimoUsuario, cmbEmprestimos;
    private JTextField txtEmprestimoData, txtEmprestimoId;
    private JLabel lblLogo, lblEditar;
    private JPanel panMain;

    /**
     * Habilita ou desabilita os objetos
     * @param enable Booleano de habilitacao/desabilitacao (true/false)
     */
    private void habilitaTexto(boolean enable) {//Método para habilitar/desabilitar botões
        cmbEmprestimos.setEnabled(!enable);
        btnEmprestimoEditar.setEnabled(!enable);

        txtEmprestimoId.setEnabled(false);
        txtEmprestimoData.setEnabled(enable);

        cmbEmprestimoLivro.setEnabled(enable);
        btnEmprestimoLivro.setEnabled(enable);

        cmbEmprestimoUsuario.setEnabled(enable && cmbEmprestimos.getSelectedIndex() == 0);
        btnEmprestimoUsuario.setEnabled(enable && cmbEmprestimos.getSelectedIndex() == 0);

        btnEmprestimoAdd.setEnabled(enable);
        btnEmprestimoClear.setEnabled(enable);
        btnEmprestimoCancel.setEnabled(enable);
    }

    /**
     * Atualiza o combo box de emprestimos
     */
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

    /**
     * Atualiza o combo box de livros
     */
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

    /**
     * Atualiza o combo box de usuarios
     */
    private void updateComboUsuarios() {//Método para exibir lista de usuários
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Usuario> usuarios = conexaoBanco.getUsuarios();
        conexaoBanco.disconnect();
        cmbEmprestimoUsuario.removeAllItems();
        for (Usuario usuario : usuarios) {
            cmbEmprestimoUsuario.addItem(usuario);
        }
    }

    /**
     * Preenche os campos da tela com a disponibilidade enviada por parametro
     * @param disponibilidade Disponibilidade a preencher
     */
    private void fillItems(Disponibilidade disponibilidade) {
        if (disponibilidade == null) { // caso seja uma insercao / invalido
            txtEmprestimoId.setText("");
            txtEmprestimoData.setText("01/01/2010");
            cmbEmprestimoLivro.setSelectedIndex(-1);
            cmbEmprestimoUsuario.setSelectedIndex(-1);
        } else {
            txtEmprestimoId.setText(disponibilidade.getId() + "");

            String data = disponibilidade.getData().toString();


            String novaData;

            try { //validação da data
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

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnEmprestimoEditar)) { //Ação ao "Editar"
            if (cmbEmprestimos.getSelectedIndex() == 0) {
                fillItems(null);
            } else {
                fillItems((Disponibilidade) cmbEmprestimos.getSelectedItem());
            }
        } else if (actionEvent.getSource().equals(btnEmprestimoClear)) { //Ação ao "Limpar"
            fillItems(null);
        } else if (actionEvent.getSource().equals(btnEmprestimoCancel)) { //Ação ao "Cancelar"
            fillItems(null);
            habilitaTexto(false);
        } else if (actionEvent.getSource().equals(btnEmprestimoAdd)) { //Ação ao "Confirmar"

            Livro livro = (Livro) cmbEmprestimoLivro.getSelectedItem();
            Usuario usuario = null;
            String data = txtEmprestimoData.getText();
            String id = txtEmprestimoId.getText();

            if (id == null || id.equals("")) {
                id = "0";
            }

            if (livro == null || data == null || data.equals("")) { //validação dos campos
                JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idDisp = Integer.parseInt(id);
            int idLivro = livro.getId();
            int idUsuario = 0;

            if (cmbEmprestimos.getSelectedIndex() == 0) { //validação dos campos
                usuario = (Usuario) cmbEmprestimoUsuario.getSelectedItem();
                if (usuario == null) {
                    JOptionPane.showMessageDialog(this, "Campos vazios...", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                idUsuario = usuario.getId();
            }

            String novaData;
            java.sql.Date date;

            try { //Validação da data
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

            if (cmbEmprestimos.getSelectedIndex() == 0) { //Confirmação da inserção/atualização da Disponibilidade
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
        } else if (actionEvent.getSource().equals(btnEmprestimoLivro)) { //Ação ao "Novo usuário"
            new EditLivroGUI();
            updateComboLivros();
        } else if (actionEvent.getSource().equals(btnEmprestimoUsuario)) { //Ação ao "Novo livro"
            new EditUsuarioGUI();
            updateComboUsuarios();
        }
    }

    /**
     * Inicializa os componentes java swing
     * Criado automaticamente pelo NetBeans
     */
    private void initComponents() {
        panMain = new JPanel();
        lblLogo = new JLabel();
        btnEmprestimoCancel = new JButton();
        btnEmprestimoEditar = new JButton();
        txtEmprestimoData = new JTextField();
        lblEditar = new JLabel();
        cmbEmprestimos = new JComboBox<>();
        txtEmprestimoId = new JTextField();
        btnEmprestimoLivro = new JButton();
        cmbEmprestimoLivro = new JComboBox<>();
        btnEmprestimoUsuario = new JButton();
        cmbEmprestimoUsuario = new JComboBox<>();
        btnEmprestimoAdd = new JButton();
        btnEmprestimoClear = new JButton();

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

        btnEmprestimoCancel.setBackground(new java.awt.Color(33, 64, 140));
        btnEmprestimoCancel.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnEmprestimoCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnEmprestimoCancel.setText("Cancelar");
        btnEmprestimoCancel.setBorderPainted(false);
        btnEmprestimoCancel.setBounds(540, 530, 180, 50);
        getContentPane().add(btnEmprestimoCancel);

        btnEmprestimoEditar.setBackground(new java.awt.Color(33, 64, 140));
        btnEmprestimoEditar.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnEmprestimoEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEmprestimoEditar.setText("Editar");
        btnEmprestimoEditar.setToolTipText("");
        btnEmprestimoEditar.setBorderPainted(false);
        btnEmprestimoEditar.setBounds(580, 300, 80, 30);
        getContentPane().add(btnEmprestimoEditar);

        txtEmprestimoData.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtEmprestimoData.setText("Data do emprestimo");
        txtEmprestimoData.setBounds(240, 380, 420, 30);
        getContentPane().add(txtEmprestimoData);

        lblEditar.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblEditar.setForeground(new java.awt.Color(51, 51, 51));
        lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditar.setText("Editar emprestimo");
        lblEditar.setBounds(150, 210, 590, 60);
        getContentPane().add(lblEditar);

        cmbEmprestimos.setBackground(new java.awt.Color(0, 51, 153));
        cmbEmprestimos.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbEmprestimos.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEmprestimos.setToolTipText("Livros na biblioteca");
        cmbEmprestimos.setBounds(240, 300, 330, 30);
        getContentPane().add(cmbEmprestimos);

        txtEmprestimoId.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtEmprestimoId.setText("ID do emprestimo");
        txtEmprestimoId.setToolTipText("");
        txtEmprestimoId.setBounds(240, 340, 420, 30);
        getContentPane().add(txtEmprestimoId);

        btnEmprestimoLivro.setBackground(new java.awt.Color(33, 64, 140));
        btnEmprestimoLivro.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnEmprestimoLivro.setForeground(new java.awt.Color(255, 255, 255));
        btnEmprestimoLivro.setText("Novo livro");
        btnEmprestimoLivro.setToolTipText("");
        btnEmprestimoLivro.setBorderPainted(false);
        btnEmprestimoLivro.setBounds(530, 420, 130, 30);
        getContentPane().add(btnEmprestimoLivro);

        btnEmprestimoUsuario.setBackground(new java.awt.Color(33, 64, 140));
        btnEmprestimoUsuario.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnEmprestimoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEmprestimoUsuario.setText("Novo usuario");
        btnEmprestimoUsuario.setToolTipText("");
        btnEmprestimoUsuario.setBorderPainted(false);
        btnEmprestimoUsuario.setBounds(530, 460, 130, 30);
        getContentPane().add(btnEmprestimoUsuario);

        cmbEmprestimos.setBackground(new java.awt.Color(0, 51, 153));
        cmbEmprestimos.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbEmprestimos.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEmprestimos.setToolTipText("Emprestimos");
        cmbEmprestimos.setBounds(240, 300, 330, 30);
        getContentPane().add(cmbEmprestimos);

        cmbEmprestimoLivro.setBackground(new java.awt.Color(0, 51, 153));
        cmbEmprestimoLivro.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbEmprestimoLivro.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEmprestimoLivro.setToolTipText("Livros na biblioteca");
        cmbEmprestimos.setBounds(240, 420, 280, 30);
        getContentPane().add(cmbEmprestimoLivro);

        cmbEmprestimoUsuario.setBackground(new java.awt.Color(0, 51, 153));
        cmbEmprestimoUsuario.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbEmprestimoUsuario.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEmprestimoUsuario.setToolTipText("Livros na biblioteca");
        cmbEmprestimoUsuario.setBounds(240, 460, 280, 30);
        getContentPane().add(cmbEmprestimoUsuario);

        btnEmprestimoAdd.setBackground(new java.awt.Color(33, 64, 140));
        btnEmprestimoAdd.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnEmprestimoAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnEmprestimoAdd.setText("Confirmar");
        btnEmprestimoAdd.setBorderPainted(false);
        btnEmprestimoAdd.setBounds(160, 530, 180, 50);
        getContentPane().add(btnEmprestimoAdd);

        btnEmprestimoClear.setBackground(new java.awt.Color(33, 64, 140));
        btnEmprestimoClear.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnEmprestimoClear.setForeground(new java.awt.Color(255, 255, 255));
        btnEmprestimoClear.setText("Limpar");
        btnEmprestimoClear.setBorderPainted(false);
        btnEmprestimoClear.setBounds(350, 530, 180, 50);
        getContentPane().add(btnEmprestimoClear);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor
     * Prepara as configuracoes necessarias
     */
    public EditEmprestimoGUI() {
        // Configuração da janela
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        // configuracoes unicas da janela
        cmbEmprestimos.setBounds(240, 300, 330, 30);
        btnEmprestimoEditar.addActionListener(this);
        cmbEmprestimoLivro.setBounds(240, 420, 280, 30);
        btnEmprestimoLivro.addActionListener(this);
        btnEmprestimoUsuario.addActionListener(this);
        btnEmprestimoAdd.addActionListener(this);
        btnEmprestimoClear.addActionListener(this);
        btnEmprestimoCancel.addActionListener(this);

        // atualiza informacoes
        habilitaTexto(false);
        updateComboEmprestimos();
        updateComboLivros();
        updateComboUsuarios();

        this.setVisible(true);
    }
}
