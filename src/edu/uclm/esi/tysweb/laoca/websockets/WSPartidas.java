package edu.uclm.esi.tysweb.laoca.websockets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dominio.Manager;
import edu.uclm.esi.tysweb.laoca.dominio.Partida;
import edu.uclm.esi.tysweb.laoca.dominio.Usuario;

@ServerEndpoint(value = "/servidorDePartidas", configurator = HttpSessionConfigurator.class)
public class WSPartidas {
	private static ConcurrentHashMap<String, Session> sesionesPorId = new ConcurrentHashMap<>();

	@OnOpen
	public void open(Session sesion, EndpointConfig config) {
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
		usuario.setWSSession(sesion);

		System.out.println("Sesion " + sesion.getId());
		sesionesPorId.put(sesion.getId(), sesion);

		broadcast("Ha llegado " + usuario.getNombre());

		Partida partida = usuario.getPartida();
		if (partida.isReady())
			partida.comenzar();
	}

	@OnClose
	public void usuarioSeVa(Session session) throws IOException {
		sesionesPorId.remove(session.getId());
		broadcast("Se ha ido un usuario.");		
	}

	@OnMessage
	public void recibir(Session session, String msg) {
		JSONObject jso = new JSONObject(msg);
		if (jso.get("tipo").equals("DADO")) {
			int idPartida = jso.getInt("idPartida");
			String jugador = jso.getString("nombreJugador");
			int dado = jso.getInt("puntos");
			System.out.println("Partida "+ idPartida + " jugador "+ jugador + " dado "+ dado);
			
			try {
				Manager.get().tirarDado(idPartida, jugador, dado);				
			} catch (Exception e) {
				System.out.println("Error al tirar el dado en el servidor.");
			}
			
		}else if(jso.get("tipo").equals("EXPULSADO")) {
			int idPartida = jso.getInt("idPartida");
			String jugador = jso.getString("nombreJugador");
			try {
				Manager.get().expulsarJugador(idPartida, jugador);
			}catch (Exception e) {
				System.out.println("Error al expulsar a un jugador.");
			}
		}

	}

	private void broadcast(String mensaje) {
		Enumeration<Session> sesiones = sesionesPorId.elements();
		while (sesiones.hasMoreElements()) {
			Session sesion = sesiones.nextElement();
			try {
				JSONObject jso = new JSONObject();
				jso.put("tipo", "DIFUSION");
				jso.put("mensaje", mensaje);
				sesion.getBasicRemote().sendText(jso.toString());
			} catch (IOException e) {
				sesionesPorId.remove(sesion.getId());
			}
		}
	}

	public static ConcurrentHashMap<String, Session> getSesionesPorId() {
		return sesionesPorId;
	}
	
	
}