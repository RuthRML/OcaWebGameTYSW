function crearPartida() {
	var nickname = document.getElementById("nombre").value;
	var numJugadores = document.getElementById("numero").value;

	if (nickname == "" || numJugadores == "" || numJugadores < 2 || numJugadores > 5) {
		alert("Para empezar una partida debes introducir un número de jugadores válido (de 2 a 5) y un nombre de usuario.");
	} else {
		var request = new XMLHttpRequest();
		request.open("post", "crearPartida.jsp");
		request.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");

		request.onreadystatechange = function() {
			if (request.readyState == 4) {
				var respuesta = JSON.parse(request.responseText);
				console.log(respuesta);
				if (respuesta.result == "OK") {
					console.log(respuesta.result);
					sessionStorage.setItem("nombre", document
							.getElementById("nombre").value);
					window.location.href = 'juego.html';
					sessionStorage.setItem("idPartida", respuesta.partida);
				} else {
					console.log(respuesta.mensaje);
					alert(respuesta.mensaje);
				}
			}
		};

		var p = {
			nombre : document.getElementById("nombre").value,
			numeroDeJugadores : document.getElementById("numero").value
		};

		request.send("p=" + JSON.stringify(p));
	}
}

function unirse() {
	var request = new XMLHttpRequest();
	request.open("post", "llegarASalaDeEspera.jsp");
	request.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			var respuesta = JSON.parse(request.responseText);
			if (respuesta.result == "OK") {
				console.log(respuesta);
				window.location.href = 'juego.html';
				sessionStorage.setItem("nombre", document.getElementById("nombre").value);
				sessionStorage.setItem("idPartida", respuesta.mensaje);
			} else {
				alert(respuesta.mensaje);
			}
		}
	};

	var p = {
		nombre : document.getElementById("nombre").value
	};

	request.send("p=" + JSON.stringify(p));
}

function cerrarSesion() {
	var request = new XMLHttpRequest();
	request.open("post", "cerrarSesion.jsp");
	request.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");

	request.onreadystatechange = function() {
		if (request.readyState == 4) {
			if (request.status == 200) {
				sessionStorage.removeItem("sesion");
				sessionStorage.removeItem("nombre");
				sessionStorage.removeItem("correo");
				window.location.href = "index.html";
			} else {
				alert("Error al cerrar sesión.");
			}
		}
	};

	var p = {
		nombre : document.getElementById("nombre").value
	};

	request.send("p=" + JSON.stringify(p));

}

var ws;

function conectarWebSocket() {
	ws = new WebSocket("ws://localhost:8080/OcaWebGameTYSW/servidorDePartidas");

	ws.onopen = function() {
		console.log("Websocket conectado.");
		var nombreJugador = document.getElementById("nombreJugador");
		nombreJugador.innerHTML = sessionStorage.getItem("nombre");
	}

	ws.onmessage = function(datos) {
		var mensaje = datos.data;
		mensaje = JSON.parse(mensaje);

		if (mensaje.tipo == "DIFUSION") {

			console.log(mensaje.mensaje);

		} else if (mensaje.tipo == "COMIENZO") {

			console.log("Comienza la partida.");
			console.log("Empieza jugando" + mensaje.jugadorConElTurno);
			actualizarBotonDado(mensaje);
			tablero.actualizarNombresFichas(mensaje.jugadores);

		} else if (mensaje.tipo == "TIRADA") {

			try {
				// Atributos de la respuesta json
				var casillaOrigen = mensaje.casillaOrigen;
				var dado = mensaje.dado;
				var destinoInicial = mensaje.destinoInicial;
				var destinoFinal = mensaje.destinoFinal;
				var mensajeLlegada = mensaje.mensaje;
				var ganador = mensaje.ganador; // solo en caso que ya haya un ganador
				var idPartida = mensaje.idPartida;
				var jugadorQueTiroElDado = mensaje.jugador;
				var mensajeAdicional = mensaje.mensajeAdicional;
				var jugadorConElTurno = mensaje.jugadorConElTurno;

				console.log(casillaOrigen);
				console.log(dado);
				console.log(destinoInicial);
				console.log(destinoFinal);
				console.log(mensajeLlegada);
				console.log(idPartida);
				console.log(jugadorQueTiroElDado);
				console.log(mensajeAdicional);

				if (destinoInicial != null && destinoFinal == null) {
					tablero.actualizarFichas(jugadorQueTiroElDado, casillaOrigen, destinoInicial);
				} else if (destinoInicial != null && destinoFinal != null) {
					tablero.actualizarFichas(jugadorQueTiroElDado, casillaOrigen, destinoFinal);
				}

				actualizarBotonDado(mensaje);

			} catch (err) {
				console.log("Error en partidas.js");
			}

			if (ganador != null) {
				alert("Ha ganado " + ganador);
				sessionStorage.removeItem("idPartida");
				ws.close();
				if (sessionStorage.getItem("correo") != null) {
					pasarVariable("lobby.html", "partida");
				} else {
					window.location.href = "lobby.html";
				}
			}

		} else if (mensaje.tipo == "EXPULSADO") {
			console.log("Ha llegado el mensaje");
			var expulsado = mensaje.usuarioExpulsado;
			tablero.eliminarExpulsado(expulsado);
			console.log(mensaje.mensaje);
			actualizarBotonDado(mensaje);
			if (mensaje.ganador != null) {
				alert("Ha ganado " + mensaje.ganador);
				ws.close();
				sessionStorage.removeItem("idPartida");
				if (sessionStorage.getItem("correo") != null) {
					pasarVariable("lobby.html", "partida");
				} else {
					window.location.href = "lobby.html";
				}

			}
		}

	}

}

function pasarVariable(pagina, procedencia) {
	pagina += "?";
	pagina += "p=" + procedencia;
	location.href = pagina;
}

function actualizarBotonDado(mensaje) {
	var btnDado = document.getElementById("btnDado");
	if (mensaje.jugadorConElTurno == sessionStorage.getItem("nombre")) {
		btnDado.disabled = false;

		time = setTimeout(function() {
			alert("Se te ha acabado el tiempo. Has sido expulsado.");
			var p = {
				tipo : 'EXPULSADO',
				idPartida : sessionStorage.getItem("idPartida"),
				nombreJugador : sessionStorage.getItem("nombre"),
			};

			ws.send(JSON.stringify(p));

			sessionStorage.removeItem("idPartida");
			ws.close();

			if (sessionStorage.getItem("correo") != null) {
				pasarVariable("lobby.html", "partida");
			} else {
				window.location.href = "lobby.html";
			}

		}, 15000);
	} else {

		btnDado.disabled = true;
	}

	document.getElementById("jugadorTurno").innerHTML = mensaje.jugadorConElTurno;
}

function salir() {

	var token = confirm("¿Seguro que quieres abandonar la partida?");
	if (token == true) {
		var p = {
			tipo : 'EXPULSADO',
			idPartida : sessionStorage.getItem("idPartida"),
			nombreJugador : sessionStorage.getItem("nombre"),
		};

		ws.send(JSON.stringify(p));

		sessionStorage.removeItem("idPartida");
		ws.close();

		if (sessionStorage.getItem("correo") != null) {
			pasarVariable("lobby.html", "partida");
		} else {
			window.location.href = "lobby.html";
		}
	}

}
