package content;

import java.util.Date;


/**Classe utilizada para representar a disponibilidade de um {@link Livro} adicionado por um usuário identificado por um número de identificação.
  *
  */
public class Disponibilidade {
    private int id, status;
    private Livro livro;
    private Date data;
    
    /**Método construtor que define o usuário que será associado instância criada da classe {@link Disponibilidade}.
      *@param id inteiro contendo o número de identificação do usuário
      */
    public Disponibilidade(int id) {
        this.id = id;
    }
    
    /**Método getter que retorna o número de identificação do usuário associado à instância da classe {@link Disponibilidade}.
      *@return inteiro que é o número de identificação do usuário associado à instância da classe {@link Disponibilidade}
      */
    public int getId() {
        return id;
    }
    
    /**Método setter que define, a partir do número de identificação, o usuário que é associado à instância da classe {@link Disponibilidade}
      *@param id inteiro que é o número de identificação do novo usuário associado à instância da classe {@link Disponibilidade}
      */
    public void setId(int id) {
        this.id = id;
    }
    
    /**Método getter que retorna um inteiro representando o status do {@link Livro} associado à instância da classe {@link Disponibilidade}.
      *return inteiro representando o status do {@link Livro} associado à instância da classe {@link Disponibilidade} 
      */
    public int getStatus() {
        return status;
    }
    
    /**Método setter que define, por meio de um inteiro dado, o status do {@link Livro} associado à instância da classe {@link Disponibilidade}.
      *@param status inteiro representando o novo status do {@link Livro} associado à instância da classe {@link Disponibilidade} 
      */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**Método getter que retorna o {@link Livro} associado à instância da classe {@link Disponibilidade}.
      *@return {@link Livro} associado à instância da classe {@link Disponibilidade}
      */
    public Livro getLivro() {
        return livro;
    }
    
    /**Método setter que define o {@link Livro} associado à instância da classe {@link Disponibilidade}.
      *@param livro novo {@link Livro} associado à instância da classe {@link Disponibilidade}
      */
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    
    /**Método getter que retorna uma instância de {@link Date} representando a data associado à instância da classe {@link Disponibilidade}.
      *@return instância de {@link Date} representando a data associado à instância da classe {@link Disponibilidade}
      */
    public Date getData() {
        return data;
    }
    
    /**Método getter que define uma instância de {@link Date} associado à instância da classe {@link Disponibilidade}.
      *@param data instância de {@link Date} associado à instância da classe {@link Disponibilidade}.
      */
    public void setData(Date data) {
        this.data = data;
    }
    
    /**Método que retorna uma {@link String} contendo o número de identificação do usuário, o nome do livro, a data e o status associados à instância da classe {@link Disponibilidade}.
      *@return uma {@link String} contendo o número de identificação do usuário, o nome do livro, a data e o status associados à instância da classe {@link Disponibilidade}
      */
    @Override
    public String toString() {
        return "(" + id + ") " + livro.getNome() + " - " + data.toString() + " [" + status + "]";
    }
}
