function crearPartida(){
	var request = new XMLHttpRequest();
	request.open("post", "crearPartida.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function(){
		if(request.readyState == 4){
			var respuesta = JSON.parse(request.responseText);
			console.log(respuesta);
			if (respuesta.result=="OK") {
				console.log(respuesta.result);
				conectarWebSocket();
				localStorage.nombre = document.getElementById("nombre").value;
				var divMensajes=document.getElementById("divMensajes");
				divMensajes.innerHTML += "Se ha conectado";
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

function unirse(){
	var request = new XMLHttpRequest();
	request.open("post", "llegarASalaDeEspera.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function(){
		if(request.readyState == 4){

			var respuesta = JSON.parse(request.responseText);
			if (respuesta.result=="OK") {
				console.log(respuesta);
				conectarWebSocket();
				localStorage.nombre = document.getElementById("nombre").value;

			}else{

				aler("error al unirse");



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
		console.log("Websocket conectado");

		//var tablero = new Tablero();
		//tablero.dibujar(svgTablero);
	}

	ws.onmessage = function (datos){
		var mensaje = datos.data;
		mensaje = JSON.parse(mensaje);

		if(mensaje.tipo == "DIFUSION"){
			console.log(mensaje.mensaje);
		}else if (mensaje.tipo =="COMIENZO"){
			addMensaje("Comienza la partida");
		}

	}

}

function comenzar(mensaje){
	var btnDado = document.getElementById("btnDado");
	if(mensaje.jugadorConElTurno==localStorage.nombre){
		btnDado.setAttribute("style","display:visible");
	}else{

		btnDado.setAttribute("style","display:none");
	}
	var spanListaJugadores=document.getElementById("spanListaJugadores");
	var jugadores= mensaje.jugadores;
	for (var i = 0; i < jugadores.length; i++) {
		r=r+jugadores[i];
		spanListaJugadores.innerHTML=r;
	}
}


function addMensaje(texto) {
	var div=document.createElement("div");
	divChat.appendChild(div);
	div.innerHTML=texto;
}











