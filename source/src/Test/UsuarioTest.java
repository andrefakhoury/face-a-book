import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import content.Livro;
import content.Usuario;

/**
 * Testador da classe usuario
 */
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
}
