public class Livro {
    private int id;
    private String nome;
    private Categoria categoria;


    public Livro(int id, String nome, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "(" + id + ") " + nome + " - " + categoria;
    }
}
