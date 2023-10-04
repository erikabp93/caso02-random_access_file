package raf;

import java.util.Objects;

/**
 * Clase utilizada para encapsular los datos de la entidad cliente.
 *
 * @author sergio
 */
public class Cliente {
	public static final byte TAM_REGISTRO = 126; 		// total bytes que ocupa el registro en memoria
	static final byte TAM_NOMBRE = 20; 	// tamaño máximo campo nombre
	static final byte TAM_APELLIDOS = 40; // tamaño máximo campo apellidos
	private short id; 					// 2 bytes. Si es negativo indica registro borrado
	private StringBuilder nombre; 		// max. 20 caracteres -> 40 bytes (2 bytes por caracter)
	private StringBuilder apellidos; 	// max. 40 caracteres -> 80 bytes
	private float saldo; 				// 4 bytes.

	public Cliente() {
	}

	public Cliente(short id, String nombre, String apellidos, float saldo) {
		this.id = id;
		this.nombre = new StringBuilder(nombre);
		//this.nombre.setLength(TAM_NOMBRE);
		this.apellidos = new StringBuilder(apellidos);
		//this.apellidos.setLength(TAM_APELLIDOS);
		this.saldo = saldo;
	}

	public short getId() {
		return this.id;
	}

	public StringBuilder getNombre() {
		return this.nombre;
	}

	public StringBuilder getApellidos() {
		return this.apellidos;
	}

	public float getSaldo() {
		return this.saldo;
	}

	public void setId(short i) {
		this.id = i;
	}

	public void setNombre(String nombre) {
		this.nombre = new StringBuilder(nombre);
		this.nombre.setLength(TAM_NOMBRE);
	}

	public void setApellidos(String apellidos) {
		this.apellidos = new StringBuilder(apellidos);
		this.apellidos.setLength(TAM_APELLIDOS);
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Cliente{" + "id=" + id + ", nombre=" + nombre.toString().trim() + ", "
				+ "apellidos=" + apellidos.toString().trim() + ", saldo=" + saldo + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, apellidos, saldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return id == other.id &&   Objects.equals(nombre.toString(), other.nombre.toString()) 
				&& Objects.equals(apellidos.toString(), other.apellidos.toString()) 
				&& Float.floatToIntBits(saldo) == Float.floatToIntBits(other.saldo);
	}

	boolean estaBorrado() {
		return this.getId() < 0;
	}

}
