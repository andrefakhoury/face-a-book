package content;

/**
 * Classe que representa uma categoria de um livro
 *
 */
public class Categoria {
    private int id;
    private String nome;
    
    /**
     * Contrutor do objeto categoria que recebe o nome da categoria e um inteiro como chave para a categoria
     * @param id - chave associada a categoria
     * @param nome - nome da categoria
     */
    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    /**
     * Função que retorna o valor contido em id
     * @return inteiro com o valor do campo id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Função que retorna o valor contido em nome
     * @return String com o nome da categoria
     */
    public String getNome() {
        return nome;
    }
    
   /**
    * Função para mudar o id de um objeto Categoria
    * @param id Inteiro que representa o novo id da categoria
    */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Função para mudar o nome de um objeto Categoria
     * @param nome String que representa o novo nome da categoria
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

   
    /**
     * Função que transforma o objeto Categoria em String no seguinte formato:
     * 		"(id) nome"
     * 
     * @return String que representa o objeto Categoria
     */
    @Override
    public String toString() {
        return "(" + id + ") " + nome;
    }
}
