package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.IOException;

import javax.websocket.Session;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;

public class Usuario {
	protected String correo;
	protected String nombre;
	protected Partida partida;
	private Session sesion;
	private int turnosSinTirar;

	private Casilla casilla;

	public Usuario(String correoJugador) throws Exception {
		this.correo = correoJugador;
	}

	public Usuario() {
		
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setWSSession(Session sesion) {
		this.sesion = sesion;
	}

	public void enviar(String jugador, int dado) {
		// TODO Auto-generated method stub

	}

	public void setCorreo(String email) {
		this.correo = email;
	}

	public void registrarUsuario(String pwd) throws Exception {
		if (!DAOUsuario.existe(this.correo))
			DAOUsuario.registrar(this, pwd);
	}

	public void enviar(JSONObject jso) throws IOException {
		this.sesion.getBasicRemote().sendText(jso.toString());

	}

	public Casilla getCasilla() {
		return this.casilla;
	}

	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}

	public int getTurnosSinTirar() {
		return this.turnosSinTirar;
	}

	public void setTurnosSinTirar(int turnosSinTirar) {
		this.turnosSinTirar = turnosSinTirar;
	}

	@Override
	public String toString() {
		return this.correo + " jugando en " + (this.partida != null ? this.partida.getId() : "ninguna ") + ", "
				+ this.casilla.getPos() + ", turnos: " + this.turnosSinTirar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
