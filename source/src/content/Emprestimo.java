package content;

import java.util.Date;

/**Classe que representa o empréstimo de um {@link Livro} por um usuário, identificado pelo seu número de identificação, que deve ser devolvido até uma determinada data de entrega
  */

public class Emprestimo {
    private int id, status;
    private Livro livro;
    private Date dataEntrega;

    /**Método construtor sem argumentos que cria um empréstimo pelo usuário identificado, por padrão, pelo número zero.
      */
    public Emprestimo() {
        this.id = 0;
    }
    
    /**Método construtor que cria um empréstimo por usuário identificado por um número de identificação dado.
      *@param id número de identificação do usuário
      */
    public Emprestimo(int id) {
        this.id = id;
    }

    /**Método getter que retorna o número de identificação do usuário associado ao empréstimo.
      *@return inteiro que é o número de identificação do usuário associado ao empréstimo
      */
    public int getId() {
        return id;
    }

    /**Método setter que define novo usuário associado ao empréstimo por meio de um inteiro que é o número de identificação do usuário.
      *@param id inteiro que é o número de identificação do usuário
      */
    public void setId(int id) {
        this.id = id;
    }
    
    /**Método getter que retorna um inteiro representando o status do empréstimo.
      *@return inteiro que representa o status do empréstimo
      */
    public int getStatus() {
        return status;
    }

    /**Método setter que define novo status do empréstimo a partir de um número inteiro dado.
      *@param status inteiro que representado o novo status do empréstimo
      */
    public void setStatus(int status) {
        this.status = status;
    }

    /**Método getter que retorna {@link Livro} associado ao empréstimo.
      *@return instância de {@link Livro} associado ao empréstimo
      */
    public Livro getLivro() {
        return livro;
    }

    /**Método setter que define {@link Livro} associado ao empréstimo.
      *@param livro instância de {@link Livro} associado ao empréstimo
      */
    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    /**Método getter que retorna {@link Date} representando a data de entrega do {@link Livro} associado ao empréstimo.
      *@return instância de {@link Date} representando a data de entrega do {@link Livro} associado ao empréstimo
      */
    public Date getDataEntrega() {
        return dataEntrega;
    }

    /**Método setter que define, por meio de uma instância de {@link Date}, a data de entrega do {@link Livro} associado ao empréstimo.
      *@param dataEntrega instância de {@link Date} que representa a data de entrega do {@link Livro} associado ao empréstimo
      */
    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
    
    /**Método que retorna uma {@link String} contendo o número de identificação do usuário e o nome do livro associados ao empréstimo, e sua respectiva data de entrega.
      *@return instância de {@link String} contendo o número de identificação do usuário e o nome do livro associados ao empréstimo, e sua respectiva data de entrega
      */
    @Override
    public String toString() {
        return "(" + id + ") " + livro.getNome() + " - " + dataEntrega.toString();
    }
}
