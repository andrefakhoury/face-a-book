import java.util.ArrayList;

public class Usuario {
    private int id;
    private String nome;
    private ArrayList<Livro> livrosProprios, livrosPegos;

    public Usuario() {
        livrosProprios = new ArrayList<>();
        livrosPegos = new ArrayList<>();
    }

    public Usuario(String nome) {

    }

    public void addLivroProprio(Livro livro) {
        livrosProprios.add(livro);
    }

    public void addLivroPego(Livro livro) {
        livrosPegos.add(livro);
    }

    @Override
    public String toString() {
        String ret = "(" + id + ") " + nome + "\n";

        ret += "Livros Proprios:\n";
        for (Livro livro : livrosProprios) {
            ret += "\t" + livro + "\n";
        }

        ret += "Livros Pegos:\n";
        for (Livro livro : livrosPegos) {
            ret += "\t" + livro + "\n";
        }

        return ret;
    }
}
