package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.concurrent.ConcurrentHashMap;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;


public class Manager {
	private ConcurrentHashMap<String, Usuario> usuarios;
	private ConcurrentHashMap<Integer, Partida> partidasPendientes;
	private ConcurrentHashMap<Integer, Partida> partidasEnJuego;
	private String webAppPath;
	private String tipoDeBroker;

	private Manager() {
		this.usuarios = new ConcurrentHashMap<>();
		this.partidasPendientes = new ConcurrentHashMap<>();
		this.partidasEnJuego = new ConcurrentHashMap<>();
	}

	public ConcurrentHashMap<Integer, Partida> getPartidasEnJuego() {
		return partidasEnJuego;
	}

	public Usuario crearPartida(String nombreJugador, int numeroDeJugadores) throws Exception {
		Usuario usuario = findUsuario(nombreJugador);
		if (usuario.getPartida() != null)
			throw new Exception(
					"El usuario ya est\u00e1 asociado a una partida. Descon\u00e9ctate para crear una nueva o unirte a otra.");
		Partida partida = new Partida(usuario, numeroDeJugadores);
		usuario.setPartida(partida);
		this.partidasPendientes.put(partida.getId(), partida);
		comprobarPartida(partida);
		return usuario;
	}

	private Usuario findUsuario(String nombreJugador) throws Exception {

		Usuario usuario = this.usuarios.get(nombreJugador);

		if (usuario == null) {
			usuario = new Usuario(nombreJugador);
			this.usuarios.put(nombreJugador, usuario);
		}
		
		return usuario;
	}

	public void setTipoDeBroker(String tipoDeBroker) {
		this.tipoDeBroker = "edu.uclm.esi.tysweb.laoca.dao." + tipoDeBroker;
	}

	public String getTipoDeBroker() {
		return tipoDeBroker;
	}

	public Usuario addJugador(String nombreJugador) throws Exception {
		if (this.partidasPendientes.isEmpty())
			throw new Exception("No hay partidas pendientes. Crea una.");
		Partida partida = this.partidasPendientes.elements().nextElement();
		Usuario usuario = findUsuario(nombreJugador);
		partida.add(usuario);
		usuario.setPartida(partida);
		comprobarPartida(partida);
		return usuario;
	}

	public void comprobarPartida(Partida partida) {
		if (partida.isReady()) {
			this.partidasEnJuego.put(partida.getId(), partida);
			this.partidasPendientes.remove(partida.getId());
		}
	}

	public void setWebAppPath(String webAppPath) {
		this.webAppPath = webAppPath;
		if (!this.webAppPath.endsWith(File.separator))
			this.webAppPath += File.separator;
	}

	public String getWebAppPath() {
		return webAppPath;
	}

	private static class ManagerHolder {
		static Manager singleton = new Manager();
	}

	public static Manager get() {
		return ManagerHolder.singleton;
	}

	public Usuario registrar(String email, String pwd, String nombre) throws Exception {
		Usuario usuario = new UsuarioRegistrado(email);
		usuario.setNombre(nombre);
		usuario.registrarUsuario(pwd);
		this.usuarios.put(usuario.getNombre(), usuario);
		return usuario;
	}

	public Usuario login(String email, String pwd) throws Exception {
		Usuario usuario = UsuarioRegistrado.login(email, pwd);
		this.usuarios.put(usuario.getNombre(), usuario);
		return usuario;
	}

	public void tirarDado(int idPartida, String jugador, int dado) throws Exception {
		Partida partida = this.partidasEnJuego.get(idPartida);
		JSONObject mensaje = partida.tirarDado(jugador, dado);
		mensaje.put("idPartida", idPartida);
		mensaje.put("jugador", jugador);
		partida.broadcast(mensaje);
		if (mensaje != null && mensaje.opt("ganador") != null) {
			terminar(partida);
		}
	}

	private void terminar(Partida partida) throws Exception {
		partida.terminar();
		partidasEnJuego.remove(partida.getId());
	}

	public void expulsarJugador(int idPartida, String jugador) throws Exception {
		Partida partida = this.partidasEnJuego.get(idPartida);
		if(partida == null) {
			partida = this.partidasPendientes.get(idPartida);
		}
		Usuario usuario = findUsuario(jugador);
		JSONObject mensaje = partida.removeJugador(usuario);
		this.usuarios.remove(usuario.getNombre(), usuario);
		partida.broadcast(mensaje);
		if(mensaje != null && mensaje.opt("ganador") != null) {
			terminar(partida);
		}				
	}
	
	public void cerrarSesion(String nombreJugador) throws Exception {
		this.usuarios.remove(nombreJugador);
	}
	
	public JSONArray calcularRanking() throws Exception {
		DAOUsuario dao = new DAOUsuario();
		return dao.getRanking();
	}

	public String recuperarPwd(String email) throws Exception {
		DAOUsuario dao = new DAOUsuario();
		String respuesta = null;
		boolean existe = dao.existe(email);
		if (existe) {
			RecuperarPwd recovery = new RecuperarPwd();
			recovery.setEmail(email);
			respuesta = null;
			recovery.generarCodigo();
			DAOUsuario.registrarCodigoRecuperacion(email, recovery.getCodigo());
			respuesta = recovery.execute();
		} else {
			throw new Exception("El usuario no existe en la base de datos");
		}
		return respuesta;

	}

	public Usuario insertarNuevaPwd(String email, String pass1, String codigo) throws Exception {

		Usuario usuario = new UsuarioRegistrado(email);
		usuario.setCorreo(email);
		usuario.registrarUsuarioNuevaPwd(pass1, codigo);
		return usuario;

	}
	
	public Usuario insertarNuevaPwdVoluntario(String email, String pass1) throws Exception {

		Usuario usuario = new UsuarioRegistrado(email);
		usuario.setCorreo(email);
		usuario.registrarUsuarioNuevaPwdVoluntario(pass1);
		return usuario;

	}
	

}