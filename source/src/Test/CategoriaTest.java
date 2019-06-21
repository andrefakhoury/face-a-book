import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import content.Categoria;

/**
 * Classe para testar a categoria
 */
class CategoriaTest {

	@Test
	void testCreate() {
		Categoria c = new Categoria(1,"teste");
		assertNotNull(c);
	}
	
	@Test
	void testGetId() {
		Categoria c = new Categoria(1,"teste");
		assertEquals(1,c.getId());
	}
	
	@Test
	void testSetId() {
		Categoria c = new Categoria(1,"teste");
		c.setId(99);
		assertEquals(99,c.getId());
	}
	
	@Test
	void testGetNome() {
		Categoria c = new Categoria(1,"teste");
		assertTrue(c.getNome().equals("teste"));
	}
	
	@Test
	void testSetNome() {
		Categoria c = new Categoria(1,"teste");
		c.setNome("testeSet");
		assertTrue(c.getNome().equals("testeSet"));
	}
	
	@Test
	void testToString() {
		Categoria c = new Categoria(1,"teste");
		assertTrue(c.toString().equals("(1) teste"));
	}

}
