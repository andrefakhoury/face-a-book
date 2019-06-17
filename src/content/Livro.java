package content;

public class Livro {
    private int id;
    private Categoria categoria;
    private String nome, autor, foto;

    public Livro() {
        this.id = 0;
    }


    public Livro(int id, Categoria categoria, String nome, String autor, String foto) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.autor = autor;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setCategoria(int id, String nome) {
        this.categoria = new Categoria(id, nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "(" + id + ") " + nome + " - " + autor + " - " + categoria.getNome();
    }
}
