package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import content.Categoria;
import content.Livro;
import content.Usuario;

class UsuarioTest {

	@Test
	void testCreate1() {
		Usuario u = new Usuario();
		assertNotNull(u);
	}
	
	@Test
	void testCreate2() {
		Usuario u = new Usuario(1,"nome");
		assertNotNull(u);
	}
	
	@Test
	void testCreate3() {
		ArrayList<Livro> livrosProprios, livrosPegos;
		livrosProprios = new ArrayList<Livro>();
        livrosPegos = new ArrayList<Livro>();
		Usuario u = new Usuario(1,"nome",livrosProprios,livrosPegos);
		assertNotNull(u);
	}
	
}
