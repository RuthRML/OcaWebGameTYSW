/**
 * Cada vez que lleguemos a una pagina interna, se hará una comprobación de que el usuario
 * está conectado
 */

function estaConectado(){
	var request = new XMLHttpRequest();	
	request.open("get","Cuentas.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			if (respuesta.result=="OK") {
				alert("OK");
				//divMensajes.innerHTML=respuesta.mensaje;
				//divDesconectar.setAttribute("style", "display:visible");
			} else
				alert("error");
				//divMensajes.innerHTML="Error: " + respuesta.mensaje;
		}
	};
	/*var p = {
		email : email.value,
		pwd   : pwd.value,
		tipoDeBroker : tipoDeBroker.value
	};
	request.send("p=" + JSON.stringify(p));
	*/	
}