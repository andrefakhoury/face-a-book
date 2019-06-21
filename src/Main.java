import GUI.*;
import banco_dados.ConexaoBanco;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        if (new ConexaoBanco().testConnection()) {
            new LoginGUI();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Houve algum erro na conexao ao banco de dados.\n" +
                    "Verifique a instalacao do PostgreSQL.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
