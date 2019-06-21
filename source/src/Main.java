import GUI.*;
import banco_dados.ConexaoBanco;

import javax.swing.*;

/**
 * Classe driver para executar pela primeira vez o Login
 * Antes de executar o programa, testa o banco de dados
 */
public class Main {

    /**
     * Funcao principal
     * @param args
     */
    public static void main(String[] args) {

        if (new ConexaoBanco().testConnection()) { // testa o banco de dados
            new LoginGUI();
        } else { // houve erro no banco de dados
            JOptionPane.showMessageDialog(null,
                    "Houve algum erro na conexao ao banco de dados.\n" +
                    "Verifique a instalacao do PostgreSQL.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
