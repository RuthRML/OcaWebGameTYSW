package edu.uclm.esi.tysweb.laoca.dominio;

public class Ficha {
	Usuario duenho;
	int numeroCasilla;
	public Ficha(Usuario due�o, int numeroCasilla) {
		super();
		this.duenho = due�o;
		this.numeroCasilla = numeroCasilla;
	}
	
	public void actualizarCasilla(int numeroCasillaNueva) {
		this.numeroCasilla=numeroCasillaNueva;
	}
	
}
