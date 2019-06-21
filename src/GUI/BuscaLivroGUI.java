package GUI;

import banco_dados.ConexaoBanco;
import content.Disponibilidade;
import content.Livro;
import content.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

//JDialog para busca de livros
public class BuscaLivroGUI extends JDialog implements ActionListener {
    private Usuario usuario;

    private JTextField txtBusca;
    private JButton btnBuscar, btnReservar;
    private JComboBox cmbLivros;
    
    
    private void updateComboLivros() {//Método para buscar os livros
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Livro> livros = conexaoBanco.getLivros(txtBusca.getText());//todos os livros encontrados
        conexaoBanco.disconnect();

        cmbLivros.removeAllItems();//remoção dos livros encontrados em buscas anteriores
        for (Livro livro : livros) {//exibição dos livros encontrados
            cmbLivros.addItem(livro);
        }
    }

    private void reservarLivro() {//Método para reservar livro selecionado
        Livro livro = (Livro) cmbLivros.getSelectedItem();
        if (livro == null) {//validação do livro
            JOptionPane.showMessageDialog(this, "Selecione um livro valido", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();
        ArrayList<Disponibilidade> disponibilidades = conexaoBanco.getDisponibilidades(livro);

        if (disponibilidades.isEmpty()) {//verificação de disṕonibilidade do livro
            JOptionPane.showMessageDialog(this, "Livro nao disponivel!", "Erro", JOptionPane.ERROR_MESSAGE);
            conexaoBanco.disconnect();
            return;
        }

        Disponibilidade disponibilidade = disponibilidades.get(0);

        java.util.Calendar cal1 = Calendar.getInstance();
        cal1.setTime(disponibilidade.getData());

        java.util.Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new java.util.Date());
        cal2.add(Calendar.DATE, 7);

        java.util.Calendar cal;
        if (cal1.before(cal2)) {
            cal = cal1;
        } else {
            cal = cal2;
        }

        java.sql.Date dataEntrega = new java.sql.Date(cal.getTime().getTime());

        if (conexaoBanco.emprestarLivro(disponibilidade, usuario, dataEntrega)) {//Exibição de confirmação do empréstimo
            JOptionPane.showMessageDialog(this, "Livro emprestado!\nPasse na biblioteca para pegar seu livro :)", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro no emprestimo!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        conexaoBanco.disconnect();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(btnBuscar)) {//Ação ao "Buscar"
            updateComboLivros();
        } else if (actionEvent.getSource().equals(btnReservar)) {//Ação ao "Reservar"
            reservarLivro();
        }
    }
    
    //INÍCIO - Construtor de BuscaLivroGUI
    public BuscaLivroGUI(Usuario usuario) {
        //INÍCIO - Configuração da janela
        this.setSize(920, 720);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.usuario = usuario;
        this.setModal(true);
        
        JPanel panMain = new JPanel(null);
        //FIM - Configuração da janela
        
        //INÍCIO - Instanciação e inserção dos itens da janela    
        txtBusca = new JTextField();
        txtBusca.setBounds(10, 10, 200, 40);
        panMain.add(txtBusca);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(215, 10, 200, 40);
        btnBuscar.addActionListener(this);
        panMain.add(btnBuscar);

        cmbLivros = new JComboBox();
        cmbLivros.setBounds(10, 55, 500, 40);
        panMain.add(cmbLivros);
        updateComboLivros();

        btnReservar = new JButton("Reservar!");
        btnReservar.setBounds(10, 100, 200, 40);
        btnReservar.addActionListener(this);
        panMain.add(btnReservar);

        this.add(panMain);
        this.setVisible(true);
        //FIM - Instanciação e inserção dos itens da janela  
    }
     //INÍCIO - Construtor de BuscaLivroGUI
}
