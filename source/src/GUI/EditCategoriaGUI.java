package GUI;

import banco_dados.ConexaoBanco;
import content.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Subclasse de JDialog - interface grafica de edicao de categoria
 */
public class EditCategoriaGUI extends JDialog implements ActionListener {
    // objetos swing
    private JButton btnCategoriaAdd, btnCategoriaCancel, btnCategoriaClear, btnCategoriaEditar;
    private JTextField txtCategoriaId, txtCategoriaNome;
    private JLabel lblLogo, lblEditar;
    private JComboBox cmbCategorias;
    private JPanel panMain;

    /**
     * Habilita ou desabilita os objetos
     * @param enable Booleano de habilitacao/desabilitacao (true/false)
     */
    private void habilitaTexto(boolean enable) {
        //Método para habilitar/desabilitar botões
        cmbCategorias.setEnabled(!enable);
        btnCategoriaEditar.setEnabled(!enable);

        txtCategoriaId.setEnabled(false);
        txtCategoriaNome.setEnabled(enable);

        btnCategoriaAdd.setEnabled(enable);
        btnCategoriaClear.setEnabled(enable);
        btnCategoriaCancel.setEnabled(enable);
    }

    /**
     * Atualiza o comboBox de categorias
     */
    private void updateComboCategoria() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Categoria> categorias = conexaoBanco.getCategorias();
        conexaoBanco.disconnect();
        cmbCategorias.removeAllItems();
        cmbCategorias.addItem("-- Adicionar --");
        for (Categoria categoria : categorias) {
            cmbCategorias.addItem(categoria);
        }
    }

    /**
     * Preenche os objetos do swing com a categoria
     * @param categoria Categoria para preencher os objetos
     */
    private void fillItems(Categoria categoria) {
        if (categoria == null) {
            txtCategoriaId.setText("");
            txtCategoriaNome.setText("");
        } else {
            txtCategoriaId.setText(categoria.getId() + "");
            txtCategoriaNome.setText(categoria.getNome());
        }

        // habilita os textos
        habilitaTexto(true);
    }

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnCategoriaEditar)) { //Ação ao "Editar"
            if (cmbCategorias.getSelectedIndex() == 0) {
                fillItems(null);
            } else {
                fillItems((Categoria) cmbCategorias.getSelectedItem());
            }
        } else if (actionEvent.getSource().equals(btnCategoriaClear)) { //Ação ao "Limpar"
            fillItems(null);
        } else if (actionEvent.getSource().equals(btnCategoriaCancel)) { //Ação ao "Cancelar"
            fillItems(null);
            habilitaTexto(false);
        } else if (actionEvent.getSource().equals(btnCategoriaAdd)) { //Ação ao Confirmar

            if (txtCategoriaNome.getText().equals("")) { //validação do nome na caixa de texto
                JOptionPane.showMessageDialog(this, "Nome invalido!", "Falha", JOptionPane.WARNING_MESSAGE);
            } else {
                String nome = txtCategoriaNome.getText();
                String id = txtCategoriaId.getText();

                if (nome == null || nome.equals("")) {
                    JOptionPane.showMessageDialog(this, "Nome invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (id == null || id.equals("")) {
                    id = "0";
                }

                Categoria categoria = new Categoria(Integer.parseInt(id), nome);

                ConexaoBanco conexaoBanco = new ConexaoBanco();
                conexaoBanco.connect();

                boolean ok = false;
                String message = "";

                if (cmbCategorias.getSelectedIndex() == 0) { //confirmação da inserção da categoria
                    ok = conexaoBanco.insertCategoria(categoria);
                    message = "Inserido com sucesso!";
                } else {
                    ok = conexaoBanco.updateCategoria(categoria);
                    message = "Editado com sucesso!";
                }

                conexaoBanco.disconnect();

                if (ok) {
                    JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    this.setVisible(false);
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
        btnCategoriaAdd = new JButton();
        btnCategoriaEditar = new JButton();
        txtCategoriaId = new JTextField();
        lblEditar = new JLabel();
        cmbCategorias = new JComboBox<>();
        txtCategoriaNome = new JTextField();
        btnCategoriaCancel = new JButton();
        btnCategoriaClear = new JButton();

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

        btnCategoriaAdd.setBackground(new java.awt.Color(33, 64, 140));
        btnCategoriaAdd.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnCategoriaAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnCategoriaAdd.setText("Confirmar");
        btnCategoriaAdd.setBorderPainted(false);
        btnCategoriaAdd.setBounds(330, 430, 230, 50);
        getContentPane().add(btnCategoriaAdd);

        btnCategoriaEditar.setBackground(new java.awt.Color(33, 64, 140));
        btnCategoriaEditar.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnCategoriaEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnCategoriaEditar.setText("Editar");
        btnCategoriaEditar.setToolTipText("");
        btnCategoriaEditar.setBorderPainted(false);
        btnCategoriaEditar.setBounds(580, 300, 80, 30);
        getContentPane().add(btnCategoriaEditar);

        txtCategoriaId.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtCategoriaId.setText("ID da Categoria");
        txtCategoriaId.setBounds(240, 380, 420, 30);
        getContentPane().add(txtCategoriaId);

        lblEditar.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblEditar.setForeground(new java.awt.Color(51, 51, 51));
        lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditar.setText("Editar categoria");
        lblEditar.setBounds(150, 210, 590, 50);
        getContentPane().add(lblEditar);

        cmbCategorias.setBackground(new java.awt.Color(0, 51, 153));
        cmbCategorias.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbCategorias.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategorias.setToolTipText("Livros na biblioteca");
        cmbCategorias.setBounds(240, 300, 330, 30);
        getContentPane().add(cmbCategorias);

        txtCategoriaNome.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtCategoriaNome.setText("Nome da categoria");
        txtCategoriaNome.setToolTipText("");
        txtCategoriaNome.setBounds(240, 340, 420, 30);
        getContentPane().add(txtCategoriaNome);

        btnCategoriaCancel.setBackground(new java.awt.Color(33, 64, 140));
        btnCategoriaCancel.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnCategoriaCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCategoriaCancel.setText("Cancelar");
        btnCategoriaCancel.setBorderPainted(false);
        btnCategoriaCancel.setBounds(330, 550, 230, 50);
        getContentPane().add(btnCategoriaCancel);

        btnCategoriaClear.setBackground(new java.awt.Color(33, 64, 140));
        btnCategoriaClear.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnCategoriaClear.setForeground(new java.awt.Color(255, 255, 255));
        btnCategoriaClear.setText("Limpar");
        btnCategoriaClear.setBorderPainted(false);
        btnCategoriaClear.setBounds(330, 490, 230, 50);
        getContentPane().add(btnCategoriaClear);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor da edicao de categoria
     * Configura os objetos da janela
     */
    public EditCategoriaGUI() {
        // Configuração da janela
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        
        // Instanciação e inserção de itens da janela
        btnCategoriaEditar.addActionListener(this);
        btnCategoriaAdd.addActionListener(this);;
        btnCategoriaClear.addActionListener(this);
        btnCategoriaCancel.addActionListener(this);

        habilitaTexto(false);
        updateComboCategoria();

        this.setVisible(true);
    }
}
