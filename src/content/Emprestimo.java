package content;

import java.util.Date;

public class Emprestimo {
    private int id, status;
    private Livro livro;
    private Date dataEntrega;

    public Emprestimo() {
        this.id = 0;
    }

    public Emprestimo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @Override
    public String toString() {
        return "(" + id + ") " + livro.getNome() + " - " + dataEntrega.toString();
    }
}
