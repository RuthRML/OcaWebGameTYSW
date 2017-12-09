package edu.uclm.esi.tysweb.laoca.dominio;

public class Dado {

	int puntos;
	String remitente;

	public Dado() {
		puntos = 0;
		remitente = "";
	}

	public void actualizar(int puntos, String remitente) {
		this.puntos = puntos;
		this.remitente = remitente;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

}
