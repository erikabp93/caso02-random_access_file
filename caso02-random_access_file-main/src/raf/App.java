package raf;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {
	static Persistencia p;

	public static void main(String[] args) {
		try {
			File f = new File("clientes.dat");
			f.delete();
			p = new Persistencia(f);

			//rellenaFichero();

			p.irFinal();
			Cliente cli = new Cliente((short) 1001, "Sergio", "Lo que sea", 1000f);
			p.guardar(cli);
			Cliente cli2 = new Cliente((short) 1, "Andres", "Aadagf", 1000f);
			p.guardar(cli2);
			Cliente cli3 = new Cliente((short) 3452, "Luis", "Ogfagfasd", 1000f);
			p.guardar(cli3);
			Cliente cli4 = new Cliente((short) 2562, "Sergio", "Arrfdr", 1000f);
			p.guardar(cli4);
			Cliente cli5 = new Cliente((short) -5, "Carlos", "Krgagag", 1000f);
			p.guardar(cli5);
			Cliente cli6 = new Cliente((short) -10, "David", "Uhadfbd", 1000f);
			p.guardar(cli6);

			p.irRegistro(1);
			System.out.println(p.leer());

			//p.borrarRegistro();

			muestraFichero();

			System.out.println("- Buscando por id");
			cli = p.buscarPorID((short) 1);
			if (cli != null) {
				System.out.println("Encontrado: " + cli);
			} else {
				System.out.println("No existe o está borrado");
			}

			System.out.println("- Buscando por aproximación de nombre");
			List<Cliente> listaClientes = p.buscarPorNombre("Sergio");
			for (Cliente c : listaClientes) {
				System.out.println(c);
			}

			muestraFichero();
			System.out.println("Huecos compactados: " + p.compactar1());
			muestraFichero();

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	private static void muestraFichero() throws IOException {
		Cliente cli;
		p.irInicio();
		System.out.println("---------------------------------");
		for (int i = 0; i < p.totalRegistros(); i++) {
			cli = p.leer();
			if (!cli.estaBorrado()) {
				System.out.println(cli);
			}
		}
		System.out.println("---------------------------------");
		System.out.println("Total registros: " + p.totalRegistros());
	}

	private static void rellenaFichero() {
		try {
			for (int i = 1; i <= 1000; i++) {
				int a = ((short) Math.round(Math.random())) == 1 ? i * (-1) : i;
				Cliente cli = new Cliente((short) a, "nomcliente" + i, "apellidoscliente" + i,
						(float) Math.random() * 1000);
				p.guardar(cli);
			}
		} catch (IOException e) {
			System.out.println("Error rellenando fichero: " + e.getMessage());
		}

	}

}
