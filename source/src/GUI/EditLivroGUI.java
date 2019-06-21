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
 * Subclasse de JDialog - interface grafica de edicao de livros
 */
public class EditLivroGUI extends JDialog implements ActionListener {
    // objetos do swing
    private JButton btnLivroEditar, btnFoto, btnLivroCategoria;
    private JButton btnLivroCancel, btnLivroAdd, btnLivroClear;
    private JComboBox cmbLivros, cmbLivroCat;
    private JLabel lblLogo, lblEditar;
    private JTextField txtLivroFoto, txtLivroId;
    private JTextField txtLivroAutor, txtLivroNome;
    private JPanel panMain;

    /**
     * Habilita ou desabilita os objetos
     * @param enable Booleano de habilitacao/desabilitacao (true/false)
     */
    private void habilitaTexto(boolean enable) {
        cmbLivros.setEnabled(!enable);
        btnLivroEditar.setEnabled(!enable);

        txtLivroId.setEnabled(false);
        txtLivroNome.setEnabled(enable);
        txtLivroAutor.setEnabled(enable);
        txtLivroFoto.setEnabled(enable);
        cmbLivroCat.setEnabled(enable);
        btnLivroCategoria.setEnabled(enable);
        btnFoto.setEnabled(enable);

        btnLivroAdd.setEnabled(enable);
        btnLivroClear.setEnabled(enable);
        btnLivroCancel.setEnabled(enable);
    }

    /**
     * Preenche os campos da tela com o livro enviada por parametro
     * @param livro livro a preencher
     */
    private void fillItems(Livro livro) {
        updateComboCategoria();

        if (livro == null) {
            txtLivroId.setText("");
            txtLivroNome.setText("");
            txtLivroAutor.setText("");
            txtLivroFoto.setText("");
            cmbLivroCat.setSelectedIndex(0);
        } else {
            txtLivroId.setText(livro.getId() + "");
            txtLivroNome.setText(livro.getNome());
            txtLivroAutor.setText(livro.getAutor());
            txtLivroFoto.setText(livro.getFoto());

            try {
                for (int i = 0; i < cmbLivroCat.getItemCount(); i++) {
                    if (((Categoria) cmbLivroCat.getItemAt(i)).getId() == livro.getCategoria().getId()) {
                        cmbLivroCat.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (Exception ex) {
                cmbLivroCat.setSelectedIndex(0);
            }
        }

        habilitaTexto(true);
    }

    /**
     * Atualiza o combo box de categorias
     */
    private void updateComboCategoria() {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Categoria> categorias = conexaoBanco.getCategorias();
        conexaoBanco.disconnect();
        cmbLivroCat.removeAllItems();
        for (Categoria categoria : categorias) {
            cmbLivroCat.addItem(categoria);
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
        cmbLivros.removeAllItems();
        cmbLivros.addItem("-- Adicionar --");
        for (Livro livro : livros) {
            cmbLivros.addItem(livro);
        }
    }

    /**
     * Gerencia o efeito dos cliques dos botoes
     * @param actionEvent evento acionado
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnLivroEditar)) { //Ação ao "Editar"
            if (cmbLivros.getSelectedIndex() == 0) {
                fillItems(null);
            } else {
                fillItems((Livro) cmbLivros.getSelectedItem());
            }
        } else if (actionEvent.getSource().equals(btnLivroClear)) { //Ação ao "Limpar"
            fillItems(null);
        } else if (actionEvent.getSource().equals(btnLivroCancel)) { //Ação ao "Cancelar"
            fillItems(null);
            habilitaTexto(false);
        } else if (actionEvent.getSource().equals(btnLivroAdd)) { //Ação ao "Confirmar"
            if (txtLivroNome.getText() == null || txtLivroNome.getText().equals("") || cmbLivroCat.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Campos invalidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = txtLivroId.getText();
            String nome = txtLivroNome.getText();
            String autor = txtLivroAutor.getText();
            String foto = txtLivroFoto.getText();
            Categoria categoria = (Categoria) cmbLivroCat.getSelectedItem();

            if (id == null || id.equals("")) {
                id = "0";
            }

            Livro livro = new Livro();
            livro.setId(Integer.parseInt(id));
            livro.setNome(nome);
            livro.setAutor(autor);
            livro.setFoto(foto);
            livro.setCategoria(categoria);

            ConexaoBanco conexaoBanco = new ConexaoBanco();
            conexaoBanco.connect();

            boolean ok = false;
            String message = "";

            if (cmbLivros.getSelectedIndex() == 0) {
                ok = conexaoBanco.insertLivro(livro);
                message = "Inserido com sucesso!";
            } else {
                ok = conexaoBanco.updateLivro(livro);
                message = "Editado com sucesso!";
            }

            conexaoBanco.disconnect();

            if (ok) {
                JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                this.setVisible(false);
            }
        } else if (actionEvent.getSource().equals(btnLivroCategoria)) {//Ação ao "Nova categoria"
            new EditCategoriaGUI();
            updateComboCategoria();
        } else if (actionEvent.getSource().equals(btnFoto)) {//Ação ao "+ foto"
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

                    txtLivroFoto.setText(outName);
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
        btnLivroCancel = new JButton();
        btnLivroEditar = new JButton();
        txtLivroFoto = new JTextField();
        lblEditar = new JLabel();
        cmbLivros = new JComboBox<>();
        txtLivroId = new JTextField();
        btnFoto = new JButton();
        btnLivroCategoria = new JButton();
        cmbLivroCat = new JComboBox<>();
        btnLivroAdd = new JButton();
        btnLivroClear = new JButton();
        txtLivroAutor = new JTextField();
        txtLivroNome = new JTextField();

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

        btnLivroCancel.setBackground(new java.awt.Color(33, 64, 140));
        btnLivroCancel.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnLivroCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnLivroCancel.setText("Cancelar");
        btnLivroCancel.setBorderPainted(false);
        btnLivroCancel.setBounds(540, 530, 180, 50);
        getContentPane().add(btnLivroCancel);

        btnLivroEditar.setBackground(new java.awt.Color(33, 64, 140));
        btnLivroEditar.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnLivroEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnLivroEditar.setText("Editar");
        btnLivroEditar.setToolTipText("");
        btnLivroEditar.setBorderPainted(false);
        btnLivroEditar.setBounds(580, 300, 80, 30);
        getContentPane().add(btnLivroEditar);

        txtLivroFoto.setEditable(false);
        txtLivroFoto.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtLivroFoto.setText("Escolha nova foto");
        txtLivroFoto.setBounds(240, 420, 360, 40);
        getContentPane().add(txtLivroFoto);

        lblEditar.setFont(new java.awt.Font("Exo ExtraBold", 0, 46)); // NOI18N
        lblEditar.setForeground(new java.awt.Color(51, 51, 51));
        lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditar.setText("Editar livro");
        lblEditar.setBounds(150, 210, 590, 40);
        getContentPane().add(lblEditar);

        cmbLivros.setBackground(new java.awt.Color(0, 51, 153));
        cmbLivros.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbLivros.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbLivros.setToolTipText("Livros na biblioteca");
        cmbLivros.setBounds(240, 300, 330, 30);
        getContentPane().add(cmbLivros);

        txtLivroId.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtLivroId.setText("ID do livro");
        txtLivroId.setToolTipText("");
        txtLivroId.setBounds(240, 340, 420, 30);
        getContentPane().add(txtLivroId);

        btnFoto.setBackground(new java.awt.Color(33, 64, 140));
        btnFoto.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnFoto.setForeground(new java.awt.Color(255, 255, 255));
        btnFoto.setIcon(new ImageIcon(Paths.get(".").toAbsolutePath() + "/images/photoIcon.png"));
        btnFoto.setToolTipText("");
        btnFoto.setBorderPainted(false);
        btnFoto.setBounds(610, 420, 50, 40);
        getContentPane().add(btnFoto);

        btnLivroCategoria.setBackground(new java.awt.Color(33, 64, 140));
        btnLivroCategoria.setFont(new java.awt.Font("Exo Black", 0, 14)); // NOI18N
        btnLivroCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnLivroCategoria.setText("Nova categoria");
        btnLivroCategoria.setToolTipText("");
        btnLivroCategoria.setBorderPainted(false);
        btnLivroCategoria.setBounds(510, 470, 150, 30);
        getContentPane().add(btnLivroCategoria);

        cmbLivroCat.setBackground(new java.awt.Color(0, 51, 153));
        cmbLivroCat.setFont(new java.awt.Font("Exo", 0, 11)); // NOI18N
        cmbLivroCat.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbLivroCat.setToolTipText("Livros na biblioteca");
        cmbLivroCat.setBounds(240, 470, 260, 30);
        getContentPane().add(cmbLivroCat);

        btnLivroAdd.setBackground(new java.awt.Color(33, 64, 140));
        btnLivroAdd.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnLivroAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnLivroAdd.setText("Confirmar");
        btnLivroAdd.setBorderPainted(false);
        btnLivroAdd.setBounds(160, 530, 180, 50);
        getContentPane().add(btnLivroAdd);

        btnLivroClear.setBackground(new java.awt.Color(33, 64, 140));
        btnLivroClear.setFont(new java.awt.Font("Exo Black", 0, 28)); // NOI18N
        btnLivroClear.setForeground(new java.awt.Color(255, 255, 255));
        btnLivroClear.setText("Limpar");
        btnLivroClear.setBorderPainted(false);
        btnLivroClear.setBounds(350, 530, 180, 50);
        getContentPane().add(btnLivroClear);

        txtLivroAutor.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtLivroAutor.setText("Autor do livro");
        txtLivroAutor.setBounds(460, 380, 200, 30);
        getContentPane().add(txtLivroAutor);

        txtLivroNome.setFont(new java.awt.Font("Exo", 0, 14)); // NOI18N
        txtLivroNome.setText("Nome do livro");
        txtLivroNome.setBounds(240, 380, 200, 30);
        getContentPane().add(txtLivroNome);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Construtor da classe
     * Prepara as configuracoes iniciais
     */
    public EditLivroGUI() {
        // Configuração da janela
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        btnLivroEditar.addActionListener(this);
        btnFoto.addActionListener(this);
        btnLivroCategoria.addActionListener(this);
        btnLivroAdd.addActionListener(this);
        btnLivroClear.addActionListener(this);
        btnLivroCancel.addActionListener(this);

        // atualiza as informacoes
        habilitaTexto(false);
        updateComboLivros();
        updateComboCategoria();

        this.setVisible(true);
    }
}
