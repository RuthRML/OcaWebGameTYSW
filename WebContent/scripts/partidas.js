function crearPartida(){
	var nickname = document.getElementById("nombre").value;
	var numJugadores = document.getElementById("numero").value;
	console.log(nickname + numJugadores);
	if(nickname == "" || numJugadores == "" || numJugadores <= 0){
		alert("Para empezar una partida debes introducir un número de jugadores válido y un nombre de usuario.");
	}else{
		var request = new XMLHttpRequest();
		request.open("post", "crearPartida.jsp");
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.onreadystatechange = function(){
			if(request.readyState == 4){
				var respuesta = JSON.parse(request.responseText);
				console.log(respuesta);
				if (respuesta.result=="OK") {
					console.log(respuesta.result);
					window.location.href='juego.html';
					sessionStorage.nombre = document.getElementById("nombre").value;
					sessionStorage.idPartida=respuesta.mensaje;				
				} else{
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

function unirse(){
	var request = new XMLHttpRequest();
	request.open("post", "llegarASalaDeEspera.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function(){
		if(request.readyState == 4){
			var respuesta = JSON.parse(request.responseText);
			if (respuesta.result=="OK") {
				console.log(respuesta);
				//conectarWebSocket();
				window.location.href='juego.html';
				sessionStorage.nombre = document.getElementById("nombre").value;
				sessionStorage.idPartida=respuesta.mensaje;
			}else{
				alert("Error al unirse.");
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
	ws = new WebSocket ("ws://localhost:8080/OcaWebGameTYSW/servidorDePartidas");

	ws.onopen = function(){
		console.log("Websocket conectado.");

		//var tablero = new Tablero();
		//tablero.dibujar(svgTablero);
	}

	ws.onmessage = function (datos){
		var mensaje = datos.data;
		mensaje = JSON.parse(mensaje);
		//console.log("Estoy aquí.");
		
		if(mensaje.tipo == "DIFUSION"){
			
			console.log(mensaje.mensaje);
			
			
		}else if (mensaje.tipo == "COMIENZO"){
			
			console.log("Comienza la partida.");
			tablero.actualizarNombresFichas(mensaje.jugadores);
			//sessionStorage.casilla = 0;
			//document.getElementById("casilla").innerHTML = sessionStorage.casilla;
			// Avisar quien tiene el turno
			
		}else if (mensaje.tipo == "TIRADA"){
			
			try{
				//Atributos de la respuesta json
				var casillaOrigen = mensaje.casillaOrigen;
				var dado = mensaje.dado;
				var destinoInicial = mensaje.destinoInicial;
				var destinoFinal = mensaje.destinoFinal;
				var mensajeLlegada = mensaje.mensaje;
				var ganador = mensaje.ganador; //solo en caso que ya haya un ganador
				var idPartida = mensaje.idPartida;
				var jugadorQueTiroElDado = mensaje.jugador;
				
				console.log(casillaOrigen);
				console.log(dado);
				console.log(destinoInicial);
				console.log(destinoFinal);
				console.log(mensajeLlegada);//console.log(ganador);
				console.log(idPartida);
				console.log(jugadorQueTiroElDado);
				
				if(destinoInicial!=null && destinoFinal==null){
					tablero.actualizarFichas(jugadorQueTiroElDado, casillaOrigen, destinoInicial);
				}else if(destinoInicial!=null && destinoFinal!=null){
					tablero.actualizarFichas(jugadorQueTiroElDado, casillaOrigen, destinoFinal);					
				}
				
				botonDado(mensaje);
			
			}catch(err){
				console.log("Error en partidas.js");
			}
			
			/*En caso que haya un ganador*/
			if(ganador!=null){
				//TODO mostrar en pantalla que ya hubo un ganador
			}	
			
		}

	}

}

function botonDado(mensaje){
	var btnDado = document.getElementById("btnDado");
	if(mensaje.jugadorConElTurno==sessionStorage.nombre){
		//btnDado.setAttribute("style", "display:visible");
		btnDado.disable = true; // AÑADIDO
	}else{
		//btnDado.setAttribute("style","display:none");
		btnDado.disable = false; // AÑADIDO
	}
	document.getElementById("jugadorTurno").innerHTML = mensaje.jugadorConElTurno;
}


function addMensaje(texto) {
	//var div=document.createElement("div");
	//divChat.appendChild(div);
	//div.innerHTML=texto;
}










