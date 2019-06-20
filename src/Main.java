import GUI.*;
import banco_dados.ConexaoBanco;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (new ConexaoBanco().testConnection()) {
            new LoginGUI();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Houve algum erro na conexao ao banco de dados.\n" +
                    "Verifique a instalacao do PostgreSQL.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
