package edu.uclm.esi.tysweb.laoca.dominio;

public class Ficha {
	Usuario duenho;
	int numeroCasilla;
	public Ficha(Usuario duenho, int numeroCasilla) {
		super();
		this.duenho = duenho;
		this.numeroCasilla = numeroCasilla;
	}
	
	public void actualizarCasilla(int numeroCasillaNueva) {
		this.numeroCasilla=numeroCasillaNueva;
	}
	
	
}
