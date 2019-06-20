package content;

import java.util.ArrayList;
/**Classe que representa um usuário do sistema de empréstimos, que pode ou não ser admnistrador.
  *O usuário possui um número de identificação, nome, foto, username e pode adicionar seus livros e pegar livros emprestados.
 **/

public class Usuario {
    private int id;
    private boolean admin;
    private String nome, foto, username;
    private ArrayList<Livro> livrosProprios, livrosPegos;
    
    /**Método construtor da classe {@link Usuario} sem argumentos.
      *Por padrão, seu número de identificação será zero e, seu nome, uma String vazia.
      *Além disso, não possui livros nem pegou livros emprestados.
      */
    public Usuario() {
        id = 0;
        nome = "";
        livrosProprios = new ArrayList<>();
        livrosPegos = new ArrayList<>();
    }
    
    /**Método construtor da classe {@link Usuario} que já define na instaciação da classe seu número de identificação e seu nome.
      *Além disso, não possui livros nem pegou livros emprestados.
      *@param id número de identificação do usuário
      *@param nome nome do usuário
      */
    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
        livrosProprios = new ArrayList<>();
        livrosPegos = new ArrayList<>();
    }
    
    /**Método construtor da classe {@link Usuario} que já define na instaciação da classe seu número de identificação, seu nome, seus livros e os livros que pegou emprestado
      *@param id número de idenficação do usuário
      *@param nome nome do usuário
      *@param livrosProprios livros do usuário
      *@param livrosPegos livros que o usuário pegou emprestado
      */
    public Usuario(int id, String nome, ArrayList<Livro> livrosProprios, ArrayList<Livro> livrosPegos) {
        this.id = id;
        this.nome = nome;
        this.livrosProprios = livrosProprios;
        this.livrosPegos = livrosPegos;
    }
    
    /**Adiciona um livro do usuário
      *@param livro livro a ser adicionado
      */
    public void addLivroProprio(Livro livro) {
        livrosProprios.add(livro);
    }
    
    /**Adiciona um livro que o usuário pegou emprestado
      *@param livro livro a ser emprestado
      */
    public void addLivroPego(Livro livro) {
        livrosPegos.add(livro);
    }

    /**Método getter para retornar o número de identificação do usuário
      *@return número de identificação do usuário
      */
    public int getId() {
        return id;
    }

    /**Método setter para definir o número de identificação do usuário
      *@param id novo número de identificação do usuário
      */
    public void setId(int id) {
        this.id = id;
    }
    
    /**Método getter para retornar o nome do usuário
      *@return {@link String} contendo o nome do usuário
      */
    public String getNome() {
        return nome;
    }
    
    /**Método setter para definir o nome do usuário
      *@param nome {@link String} contendo o novo nome do usuário
      */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**Método getter para retornar a foto do usuário
      *@return {@link String} contendo a foto do usuário
      */
    public String getFoto() {
        return foto;
    }

    /**Método setter para definir a foto do usuário
      *@param foto {@link String} contendo a nova foto do usuário
      */
    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    /**Método getter para retornar o username do usuário
      *@return {@link String} contendo o username do usuário
      */
    public String getUsername() {
        return username;
    }
    
    /**Método setter para definir o novo username do usuário
      *@param username {@link String} contendo o novo username do usuário
      */
    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    /**Método retorna um booleano indicando se o usuário é admnistrador. Retorna true se for administrador, e false, caso contrário.
      *@return valor booleano indicando se o usuário é administrador
      */
    public boolean isAdmin() {
        return admin;
    }
    
    /**Método que define se o usuário será administrador ou não. Convencionalmente, true indica que o usuário é administrador, enquanto false indica o contrário.
      *@param admin booleano indicando se usuário será administrador ou não
      */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    /**Método getter que retorna um {@link ArrayList}<{@link Livro}> contendo os livros que o usuário possui.
      *@return instância de {@link ArrayList}<{@link Livro}> contendo os livros que o usuário possui
     */
    public ArrayList<Livro> getLivrosProprios() {
        return livrosProprios;
    }
    
    /**Método setter que define, a partir de um {@link ArrayList}<{@link Livro}>, os livros que o usuário possuirá.
      *@param livrosProprios instância de {@link ArrayList}<{@link Livro}> contendo os livros que o usuário possuirá
      */
    public void setLivrosProprios(ArrayList<Livro> livrosProprios) {
        this.livrosProprios = livrosProprios;
    }
    
    /**Método getter que retorna um {@link ArrayList}<{@link Livro}> contendo os livros que o usuário pegou emprestado.
      *@return instância de {@link ArrayList}<{@link Livro}> contendo os livros que o usuário pegou emprestado
      */
    public ArrayList<Livro> getLivrosPegos() {
        return livrosPegos;
    }

    /**Método setter que define, a partir de um {@link ArrayList}<{@link Livro}>, os livros que o usuário terá pego emprestado.
      *@param livrosPegos instância de {@link ArrayList}<{@link Livro}> contendo os livros que o usuário terá pego emprestado
      */
    public void setLivrosPegos(ArrayList<Livro> livrosPegos) {
        this.livrosPegos = livrosPegos;
    }
    
    /**Método que retorna uma instância de {@link String} contendo o número de identificação, o nome, e o username do usuário.
      *@return instância de {@link String} contendo o número de identificação, o nome, e o username do usuário
     */
    @Override
    public String toString() {
        String ret = "(" + id + ") " + nome + " - " + username + "\n";

//        ret += "Livros Proprios:\n";
//        for (Livro livro : livrosProprios) {
//            ret += "\t" + livro + "\n";
//        }
//
//        ret += "Livros Pegos:\n";
//        for (Livro livro : livrosPegos) {
//            ret += "\t" + livro + "\n";
//        }

        return ret;
    }
}
