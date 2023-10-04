package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import raf.Cliente;
import raf.Persistencia;

class TestPersistencia {
	static Cliente c1, c2, c3, c4, c5, c6, c7, c8;
	static Persistencia persistenciaTest, persistenciaTest2;

	final static String NOMBRE_ARCHIVO_TEST1 = "clientes1.dat";
	final static String NOMBRE_ARCHIVO_TEST2 = "clientes.dat";
	final static String NOMBRE_ARCHIVO_TEST2_BAK = "clientes.bak";
	final static String NOMBRE_ARCHIVO_TEST2_RESULTADO = "compactado.dat";

	@BeforeAll
	static void setUpBeforeClass() {

		// Preparación test1 y 2
		File archivoTest1 = new File(NOMBRE_ARCHIVO_TEST1);
		if (archivoTest1.exists()) {
			archivoTest1.delete();
		}
		c1 = new Cliente((short) 1, "Sergio", "Galisteo", 100.1f);
		c2 = new Cliente((short) 2, "Camilo", "Juan Casillas", 200.2f);
		c3 = new Cliente((short) -3, "Empar", "Carrasquer", 300.3f);
		c4 = new Cliente((short) -4, "Ivan", "Martos", 400.4f);
		c5 = new Cliente((short) 5, "Sergio", "Segura", 500.5f);
		c6 = new Cliente((short) -6, "Francesc", "Torro", 600.6f);
		c7 = new Cliente((short) 7, "Carlos", "Juan", 700.7f);
		c8 = new Cliente((short) -8, "Roberto", "Hidalgo", 800.8f);
		try {
			persistenciaTest = new Persistencia(NOMBRE_ARCHIVO_TEST1);
			persistenciaTest.guardar(c1);
			persistenciaTest.guardar(c2);
			persistenciaTest.guardar(c3);
			persistenciaTest.guardar(c4);
			persistenciaTest.guardar(c5);
			persistenciaTest.guardar(c6);
			persistenciaTest.guardar(c7);
			persistenciaTest.guardar(c8);
		} catch (IOException e) {
			fail("El test falla al preparar fichero de pruebas - test1");
		}

		// Preparación test3
		File archivoTest2 = new File(NOMBRE_ARCHIVO_TEST2);
		File archivoTest2bak = new File(NOMBRE_ARCHIVO_TEST2_BAK);
		try {
			if (archivoTest2.exists())
				archivoTest2.delete();
			Files.copy(archivoTest2bak.toPath(), archivoTest2.toPath());
			persistenciaTest2 = new Persistencia(NOMBRE_ARCHIVO_TEST2);
		} catch (IOException e) {
			fail("El test falla al preparar fichero de pruebas - test3");
		}

	}

	@Test
	@Order(1)
	void testBuscarClientePorID() {
		Cliente buscar1 = c1;
		Cliente buscar2 = c7;
		try {
			assertEquals(buscar1, persistenciaTest.buscarPorID((short) 1));
			assertEquals(buscar2, persistenciaTest.buscarPorID((short) 7));
			assertNull(persistenciaTest.buscarPorID((short) 4));
			assertNull(persistenciaTest.buscarPorID((short) 9));
		} catch (IOException e) {
			fail("El test falla durante test1: buscar por id");
		}
	}
	
	@Test
	@Order(2)
	void testBuscarClientePorNombre() {
		List<Cliente> clientesTest1 = new ArrayList<Cliente>();
		List<Cliente> clientesTest2 = new ArrayList<Cliente>();
		clientesTest1.add(c1);
		clientesTest1.add(c5);
		clientesTest2.add(c2);
		clientesTest2.add(c7);
		try {
			assertArrayEquals(clientesTest1.toArray(), persistenciaTest.buscarPorNombre("Sergio").toArray());
			assertArrayEquals(clientesTest2.toArray(), persistenciaTest.buscarPorNombre("Juan").toArray());
			assertEquals(persistenciaTest.buscarPorNombre("Pedro").size(), 0);
		} catch (IOException e) {
			fail("El test falla durante test2: buscar por nombre cliente");
		}
	}

	@Test
	@Order(3)
	void testCompactarFichero() {
		File archivoTest2 = new File(NOMBRE_ARCHIVO_TEST2);
		File archivoTest2bak = new File(NOMBRE_ARCHIVO_TEST2_BAK);
		File archivoTest2Resultado = new File(NOMBRE_ARCHIVO_TEST2_RESULTADO);
		try {
			long compactados = persistenciaTest2.compactar1() * Cliente.TAM_REGISTRO;			
			assertEquals(archivoTest2bak.length() - archivoTest2Resultado.length(), compactados);
			assertEquals(archivoTest2Resultado.length(), archivoTest2.length());
		} catch (IOException e) {
			fail("El test falla durante test2: compartar");
		}

	}

}
