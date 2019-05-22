
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Esta classe foi desenhada para facilitar a entrada de dados
 * pelo teclado. Basicamente ela implementa funções para
 * ler strings, inteiros e doubles. 
 * Ela não faz verificações se o que foi digitado realmente
 * é o esperado.
 * @author delamaro
 *
 */
public class EntradaTeclado {
    /**
     * Esse atributo é usado para crar-se um Reader a partir da entrada padrão.
     */
    static private InputStreamReader isr = new InputStreamReader(System.in);

    /**
     * Usado para criar um BufferedReader a partir da entrada padrão;
     */
    static private BufferedReader br = new BufferedReader(isr);

    /**
     * Le um string digitado pelo teclado, até que seja pressionado
     * um enter. Ou seja, le a linha toda.
     * @return o string que foi digitado pelo usuário.
     * @throws IOException Essa exceção é lançada quando o método não consegue ler o
     * string a partir do teclado.
     */
    public static String leString() throws IOException {
        String x;
        x = br.readLine();
        return x;

    }

    /**
     * Le um string do teclado (uma linha toda) e tenta transformá-lo num inteiro.
     * Porém não faz qualquer verificação sobre a validade do dado digitado.
     * @return  o valor inteiro digitado pelo usuário.
     * @throws IOException Lançada ao chamar leString
     * @throws NumberFormatException Lançada ao tentar converter o String lido em número inteiro
     */
    public static int leInt() throws IOException, NumberFormatException  {
        String x = leString();
        return Integer.parseInt(x);
    }

    /**
     * Le um string do teclado (uma linha toda) e tenta transformá-lo num double.
     * Porém não faz qualquer verificação sobre a validade do dado digitado.
     * @return  o valor double digitado pelo usuário.
     * @throws IOException Lançada ao chamar leString
     * @throws NumberFormatException Lançada ao tentar converter o String lido em número double
     */

    public static double leDouble() throws IOException, NumberFormatException {
        String x = leString();
        return Double.parseDouble(x);

    }
}