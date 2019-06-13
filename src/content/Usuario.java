package content;

import java.util.ArrayList;

public class Usuario {
    private int id;
    private boolean admin;
    private String nome, foto, username, password;
    private ArrayList<Livro> livrosProprios, livrosPegos;

    public Usuario() {
        id = 0;
        nome = "";
        livrosProprios = new ArrayList<>();
        livrosPegos = new ArrayList<>();
    }

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
        livrosProprios = new ArrayList<>();
        livrosPegos = new ArrayList<>();
    }

    public Usuario(int id, String nome, ArrayList<Livro> livrosProprios, ArrayList<Livro> livrosPegos) {
        this.id = id;
        this.nome = nome;
        this.livrosProprios = livrosProprios;
        this.livrosPegos = livrosPegos;
    }

    public void addLivroProprio(Livro livro) {
        livrosProprios.add(livro);
    }

    public void addLivroPego(Livro livro) {
        livrosPegos.add(livro);
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<Livro> getLivrosProprios() {
        return livrosProprios;
    }

    public void setLivrosProprios(ArrayList<Livro> livrosProprios) {
        this.livrosProprios = livrosProprios;
    }

    public ArrayList<Livro> getLivrosPegos() {
        return livrosPegos;
    }

    public void setLivrosPegos(ArrayList<Livro> livrosPegos) {
        this.livrosPegos = livrosPegos;
    }

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
