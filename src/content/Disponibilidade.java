package content;

import java.util.Date;

public class Disponibilidade {
    private int id, status;
    private Livro livro;
    private Date data;

    public Disponibilidade(int id) {
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "(" + id + ") " + livro.getNome() + " - " + data.toString() + " [" + status + "]";
    }
}
