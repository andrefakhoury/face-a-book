package GUI;

import banco_dados.ConexaoBanco;
import content.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

//JDialog para editar livro
public class EditLivroGUI extends JDialog implements ActionListener {

    private JComboBox cmbLivros;
    private JButton btnLivroEditar;

    private JButton btnLivroAdd, btnLivroClear, btnLivroCancel;
    private JTextField txtLivroId, txtLivroNome, txtLivroAutor, txtLivroFoto;
    private JComboBox cmbLivroCat;
    private JButton btnLivroCategoria, btnFoto;
    
    private void habilitaTexto(boolean enable) {//Método para habilitar/desabilitar botões
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

    private void fillItems(Livro livro) {//Método para exibir livro
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

    private void updateComboCategoria() {//Método para exibir lista de categorias
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Categoria> categorias = conexaoBanco.getCategorias();
        conexaoBanco.disconnect();
        cmbLivroCat.removeAllItems();
        for (Categoria categoria : categorias) {
            cmbLivroCat.addItem(categoria);
        }
    }

    private void updateComboLivros() {//Método para exibir lista de livros
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnLivroEditar)) {//Ação ao "Editar"
            if (cmbLivros.getSelectedIndex() == 0) {
                fillItems(null);
            } else {
                fillItems((Livro) cmbLivros.getSelectedItem());
            }
        } else if (actionEvent.getSource().equals(btnLivroClear)) {//Ação ao "Limpar"
            fillItems(null);
        } else if (actionEvent.getSource().equals(btnLivroCancel)) {//Ação ao "Cancelar"
            fillItems(null);
            habilitaTexto(false);
        } else if (actionEvent.getSource().equals(btnLivroAdd)) {//Ação ao "Confirmar"
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

                    txtLivroFoto.setText(outName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao selecionar a imagem\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    //INÍCIO - Construtor de EditLivroGUI
    public EditLivroGUI() {
        //INÍCIO - Configuração da janela
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        JPanel panMain = new JPanel(null);
        //FIM - Configuração da janela
        
        //INÍCIO - Instanciação e inserção de itens da janela
        cmbLivros = new JComboBox();
        cmbLivros.setBounds(10, 10, 200, 20);
        panMain.add(cmbLivros);

        btnLivroEditar = new JButton("Editar");
        btnLivroEditar.setBounds(210, 10, 100, 20);
        btnLivroEditar.addActionListener(this);
        panMain.add(btnLivroEditar);

        txtLivroId = new JTextField();
        txtLivroId.setToolTipText("ID");
        txtLivroId.setBounds(10, 30, 200, 20);
        panMain.add(txtLivroId);

        txtLivroNome = new JTextField();
        txtLivroNome.setToolTipText("Nome");
        txtLivroNome.setBounds(10, 50, 200, 20);
        panMain.add(txtLivroNome);

        txtLivroAutor = new JTextField();
        txtLivroAutor.setToolTipText("Autor");
        txtLivroAutor.setBounds(10, 70, 200, 20);
        panMain.add(txtLivroAutor);

        txtLivroFoto = new JTextField();
        txtLivroFoto.setToolTipText("Foto");
        txtLivroFoto.setBounds(10, 90, 200, 20);
        panMain.add(txtLivroFoto);

        btnFoto = new JButton("+ Foto");
        btnFoto.addActionListener(this);
        btnFoto.setBounds(210, 90, 100, 20);
        panMain.add(btnFoto);

        cmbLivroCat = new JComboBox();
        cmbLivroCat.setBounds(10, 110, 200, 20);
        panMain.add(cmbLivroCat);

        btnLivroCategoria = new JButton("Nova Categoria");
        btnLivroCategoria.setBounds(10, 130, 200, 20);
        btnLivroCategoria.addActionListener(this);
        panMain.add(btnLivroCategoria);

        btnLivroAdd = new JButton("Confirmar");
        btnLivroAdd.addActionListener(this);
        btnLivroAdd.setBounds(10, 150, 200, 20);
        panMain.add(btnLivroAdd);

        btnLivroClear = new JButton("Limpar");
        btnLivroClear.addActionListener(this);
        btnLivroClear.setBounds(10, 170, 200, 20);
        panMain.add(btnLivroClear);

        btnLivroCancel = new JButton("Cancelar");
        btnLivroCancel.addActionListener(this);
        btnLivroCancel.setBounds(10, 190, 200, 20);
        panMain.add(btnLivroCancel);

        habilitaTexto(false);

        updateComboLivros();
        updateComboCategoria();

        this.add(panMain);
        this.setVisible(true);
        //INÍCIO - Instanciação e inserção de itens da janela
    }
    //FIM - Construtor de EditLivroGUI
}
