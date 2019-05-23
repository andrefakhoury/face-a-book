import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();

        conexaoBanco.test();

        conexaoBanco.disconnect();
    }
}
