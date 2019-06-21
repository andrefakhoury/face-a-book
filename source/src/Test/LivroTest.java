import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import content.Categoria;
import content.Livro;

/**
 * Testador a classe livro
 */
class LivroTest {

	@Test
	void testCreate() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		assertNotNull(l);
	}
	
	@Test
	void testGetId() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		assertEquals(l.getId(),1);
	}
	
	@Test
	void testGetCategoria() {
		Categoria c = new Categoria(1,"Categoria");
		Livro l = new Livro(1, c, "nome", "autor", "foto");
		assertEquals(l.getCategoria(),c);//se sao o mesmo objeto(ponteiro)
	}
	
	@Test
	void testGetNome() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		assertTrue(l.getNome().equals("nome"));
	}
	
	@Test
	void testGetAutor() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		assertTrue(l.getAutor().equals("autor"));
	}
	
	@Test
	void testGetFoto() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		assertTrue(l.getFoto().equals("foto"));
	}
	
	@Test
	void testSetId() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		l.setId(10);
		assertEquals(l.getId(),10);
	}
	
	@Test
	void testSetCategoria() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		Categoria c = new Categoria(2,"CategoriaNova");
		l.setCategoria(c);
		assertEquals(l.getCategoria(),c);//se sao o mesmo objeto(ponteiro)
	}
	
	@Test
	void testSetNome() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		l.setNome("nomeNovo");
		assertTrue(l.getNome().equals("nomeNovo"));
	}
	
	@Test
	void testSetAutor() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		l.setAutor("AutorNovo");
		assertTrue(l.getAutor().equals("AutorNovo"));
	}
	
	@Test
	void testSetFoto() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		l.setFoto("FotoNova");
		assertTrue(l.getFoto().equals("FotoNova"));
	}
	
	@Test
	void testToString() {
		Livro l = new Livro(1, new Categoria(1,"Categoria"), "nome", "autor", "foto");
		assertTrue(l.toString().equals("(1) nome - autor - (1) Categoria"));
	}

}
