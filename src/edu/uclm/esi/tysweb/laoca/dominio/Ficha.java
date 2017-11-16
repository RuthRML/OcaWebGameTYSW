package edu.uclm.esi.tysweb.laoca.dominio;

public class Ficha {
	Usuario dueño;
	int numeroCasilla;
	public Ficha(Usuario dueño, int numeroCasilla) {
		super();
		this.dueño = dueño;
		this.numeroCasilla = numeroCasilla;
	}
	
	public void actualizarCasilla(int numeroCasillaNueva) {
		this.numeroCasilla=numeroCasillaNueva;
	}
	
	
}
