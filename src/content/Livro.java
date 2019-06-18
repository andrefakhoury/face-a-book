package content;

/**
 * Classe que representa um livro e suas caracteristicas
 * int id - chave do livro
 * Categoria categoria - categoria do livro
 * String nome - nome do livro
 * String autor - autor do livro
 * String foto - caminho nos diretorios para arquivo da foto
 *
 */
public class Livro {
    private int id;
    private Categoria categoria;
    private String nome, autor, foto;
    
    /**
     *Contrutor que cria livro com id = 0 e outros elementos nulos 
     */
    public Livro() {
        this.id = 0;
    }
    
    /**
     * 
     * @param id - inteiro com chave do livro
     * @param categoria - objeto Categoria que representa a categoria do livro
     * @param nome - String nome do livro
     * @param autor  String autor do livro
     * @param foto - String caminho nos diretorios para arquivo da foto
     */
    public Livro(int id, Categoria categoria, String nome, String autor, String foto) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
        this.autor = autor;
        this.foto = foto;
    }

    /**
     * Função que retorna o valor contido em id
     * @return inteiro com o valor do campo id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Função para mudar o id de um objeto Livro
     * @param id Inteiro que representa o novo id do livro
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Função que retorna o objeto Categoria que representa a categoria do livro
     * @return objeto Categoria que representa a categoria do livro
     */
    public Categoria getCategoria() {
        return categoria;
    }
    
    /**
     * Função para mudar a categoria de um objeto Livro
     * @param categoria Objeto Categoria que sera a nova categoria do livro
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    /**
     * Função para mudar a categoria de um objeto Livro a partir de seu id e nome
     * @param id Id da nova categoria
     * @param nome Nome da nova categoria
     */
    public void setCategoria(int id, String nome) {
        this.categoria = new Categoria(id, nome);
    }
    
    /**
     * Função que retorna o nome do livro
     * @return String nome do livro
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Função para mudar o nome do Livro
     * @param nome String nome que será o novo nome do livro
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Função que retorna o autor do livro
     * @return String autor do livro
     */
    public String getAutor() {
        return autor;
    }
    
    /**
     * Função para mudar o autor do Livro
     * @param autor String autor que será o novo autor do livro
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    /**
     * Função que retorna a String caminho nos diretorios para arquivo da foto do livro
     * @return String caminho nos diretorios para arquivo da foto
     */
    public String getFoto() {
        return foto;
    }
    
    /**
     * Função para mudar a foto do Livro
     * @param foto String caminho nos diretorios para arquivo da foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    /**
     * Função que cria uma representação em String do objeto Livro
     */
    @Override
    public String toString() {
        return "(" + id + ") " + nome + " - " + autor + " - " + categoria;
    }
}
