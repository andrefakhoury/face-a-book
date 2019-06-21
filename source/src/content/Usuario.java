package content;

/**Classe que representa um usuário do sistema de empréstimos, que pode ou não ser admnistrador.
  *O usuário possui um número de identificação, nome, foto e username.
 **/

public class Usuario {
    private int id;
    private boolean admin;
    private String nome, foto, username;
    
    /**Método construtor da classe {@link Usuario} sem argumentos.
      *Por padrão, seu número de identificação será zero e, seu nome, uma String vazia.
      */
    public Usuario() {
        id = 0;
        nome = "";
    }
    
    /**Método construtor da classe {@link Usuario} que já define na instaciação da classe seu número de identificação e seu nome.
      *@param id número de identificação do usuário
      *@param nome nome do usuário
      */
    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
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

    /**Método que retorna uma instância de {@link String} contendo o número de identificação, o nome, e o username do usuário.
      *@return instância de {@link String} contendo o número de identificação, o nome, e o username do usuário
     */
    @Override
    public String toString() {
        String ret = "(" + id + ") " + nome + " - " + username + "\n";

        return ret;
    }
}
