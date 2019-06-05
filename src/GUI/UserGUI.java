package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import content.*;

public class UserGUI extends JFrame implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
//        if (actionEvent.getSource().equals()) {
//
//
//        }
    }

    public UserGUI(Usuario usuario) {
        super("Pagina do usuario");
        this.setSize(920, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panMain = new JPanel(null);

//        ImageIcon icon = new ImageIcon();
//        Image im;
//
//        try {
//            icon = new ImageIcon(file);
//        } catch (Exception ex) {
//            icon = new ImageIcon("./images/ERROR.jpg");
//        } finally {
//            im = icon.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_DEFAULT);
//        }
//
//        return new ImageIcon(im);

        JLabel lblFoto = new JLabel();
        lblFoto.setBounds(700, 10, 200, 230);
        lblFoto.setIcon(new ImageIcon(new ImageIcon("./images/profile.png").getImage().getScaledInstance(200, 300, Image.SCALE_DEFAULT)));
        panMain.add(lblFoto);

        JLabel lblInfo = new JLabel(usuario.getId() + " " + usuario.getNome() + " " + usuario.getUsername());
        lblInfo.setBounds(10, 10, 300, 20);
        panMain.add(lblInfo);

        JPanel panEmprestimos = new JPanel(null);
        panEmprestimos.setBounds(10, 40, 600, 300);
        panEmprestimos.setBackground(new Color(0xe1e1e1));

        panMain.add(panEmprestimos);

        this.add(panMain);
        this.setVisible(true);

    }


}
