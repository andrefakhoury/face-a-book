import java.io.IOException;

public class Main {

    public static int menu() throws IOException {

        System.out.println("Digite o que quer fazer:");
        System.out.println("1 - Ver pagina do usuario");
        System.out.println("2 - Consultar livros");
        System.out.println("0 - Sair");

        int op = EntradaTeclado.leInt();

        if (op >= 0 && op <= 2) {
            return op;
        } else {
            return menu();
        }
    }

    public static void paginaUsuario(Usuario usuario) {
        System.out.println(usuario);
    }

    public static void consultaLivros() {
        
    }

    public static void main(String[] args) throws IOException {
        ConexaoBanco conexaoBanco = new ConexaoBanco();
        conexaoBanco.connect();

        System.out.println("Digite seu nome: ");
        String username = EntradaTeclado.leString();

        System.out.println("Digite sua senha: ");
        String password = EntradaTeclado.leString();

        Usuario usuario = conexaoBanco.getUsuario(username, password);

        switch (menu()) {
            case 1: paginaUsuario(usuario);
                    break;
            case 2: consultaLivros();
                    break;
            case 0: System.exit(0);
        }

        conexaoBanco.disconnect();
    }
}
