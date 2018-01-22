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
				sessionStorage.nombre = respuesta.nombreUsuario;						
				window.location.href='lobby.html';
			} else{
				console.log("Error.");
			}
		}
	};
	request.send();
}

function login() {

	var emailLogin = document.getElementById("correo").value;
	var pass1 = document.getElementById("pwd1").value;
	//var divLogin = document.getElementById("divLogin");
	//var mensajeRegistro = document.getElementById("divRegistro");

	var reqLogin = new XMLHttpRequest();

	reqLogin.open("post", "login.jsp");
	reqLogin.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	reqLogin.onreadystatechange = function() {
		if (reqLogin.readyState == 4) {
			if (reqLogin.status == 200) {
				var result = JSON.parse(reqLogin.responseText);
				if (result.resultado == "OK") {
					document.getElementById("correo").value = "";
					document.getElementById("pwd1").value = "";
					alert("Login exitoso, " + emailLogin);
					sessionStorage.nombre = result.nombreUsuario;						
					window.location.href='lobby.html';
				} else {
					console.log(result.mensaje);
					//alert("Error: " + result.mensaje);
					//mensajeRegistro.innerHTML = result.resultado;
				}
			} else {
				console.log("Error en la request.");
			}
		}
	};

	var p = {
			tipo : "USERANDPWD",
			email : emailLogin,
			pwd1 : pass1
	};

	reqLogin.send("p=" + JSON.stringify(p));
}

function loginCuentaRedSocial(emailLogin, id) {

	var divLogin = document.getElementById("divLogin");
	//var mensajeRegistro = document.getElementById("divRegistro");

	var reqLogin = new XMLHttpRequest();

	reqLogin.open("post", "login.jsp");
	reqLogin.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	reqLogin.onreadystatechange = function() {
		if (reqLogin.readyState == 4) {
			if (reqLogin.status == 200) {
				var result = JSON.parse(reqLogin.responseText);
				if (result.resultado == "OK") {
					document.getElementById("correo").value = "";
					document.getElementById("pwd1").value = "";
					alert("Login exitoso, " + emailLogin);
					sessionStorage.nombre = result.nombreUsuario;						
					window.location.href='lobby.html';
				} else {
					console.log(result);
					alert("Error: " + result.mensaje);
					//mensajeRegistro.innerHTML = result.resultado;
				}
			} else {
				alert("Algo pasa");
			}
		}
	};
	var p = {
			tipo : "REDSOCIAL",
			email : emailLogin,
			pwd1 : id
	};

	reqLogin.send("p=" + JSON.stringify(p));
}

function registro() {

	var nombre = document.getElementById("nombre").value;
	var emailRegistro = document.getElementById("correo").value;
	var pass1 = document.getElementById("pwd1").value;
	var pass2 = document.getElementById("pwd2").value;
	//var idSession=document.getElementById("idSession").value;
	//var divRegistro = document.getElementById("divRegistro");
	var mensajeRegistro = document.getElementById("msgRegistro");

	var reqRegistrar = new XMLHttpRequest();
	reqRegistrar.open("post", "Registrar.jsp");
	reqRegistrar.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	reqRegistrar.onreadystatechange = function() {
		if (reqRegistrar.readyState == 4) {
			if (reqRegistrar.status == 200) {
				var result = JSON.parse(reqRegistrar.responseText);
				if (result.resultado == "OK") {

					document.getElementById("nombre").value = "";
					document.getElementById("correo").value = "";
					document.getElementById("pwd1").value = "";
					document.getElementById("pwd2").value = "";

					alert("Registro exitoso, " + emailRegistro);

					sessionStorage.setItem("nombre", result.nombreUsuario);						
					window.location.href='lobby.html';
				} else {
					mensajeRegistro.innerHTML = result.mensaje;
				}
			} else {
				alert("Error en la request al registrar usuario.");
			}
		}
	};
	
	var p = {
			nombre : nombre,
			tipo : "USERANDPWD",
			email : emailRegistro,
			pwd1 : pass1,
			pwd2 : pass2
	};

	reqRegistrar.send("p=" + JSON.stringify(p));
}


function registroCuentaRedSocial(nombreUser, emailRegistro, id) {

	//var req = emailRegistro = document.getElementById("correo").value;
	//var req=nombreUsuario=document.getElementById("nombreUsuario").value;
	//var pass1 = document.getElementById("pwd1").value;
	//var pass2 = document.getElementById("pwd2").value;
	//var idSession=document.getElementById("idSession").value;
	var divRegistro = document.getElementById("divRegistro");
	var mensajeRegistro = document.getElementById("divRegistro");

	var reqRegistrar = new XMLHttpRequest();

	reqRegistrar.open("post", "Registrar.jsp");
	reqRegistrar.setRequestHeader("Content-Type",
	"application/x-www-form-urlencoded");
	reqRegistrar.onreadystatechange = function() {
		if (reqRegistrar.readyState == 4) {
			if (reqRegistrar.status == 200) {
				var result = JSON.parse(reqRegistrar.responseText);
				if (result.resultado == "OK") {

					document.getElementById("nombreUsuario").value = "";
					document.getElementById("email").value = "";
					document.getElementById("pwd1").value = "";
					document.getElementById("pwd2").value = "";
					alert("Registro exitoso, " + emailRegistro);

					sessionStorage.nombre = result.nombreUsuario;						
					window.location.href='lobby.html';
				} else {
					console.log(result);
					alert("Error: " + result.mensaje);
					mensajeRegistro.innerHTML = result.resultado;
				}
			} else {
				alert("Algo pasa");
			}
		}
	};
	var p = {
			nombre : nombreUser,
			tipo : "REDSOCIAL",
			email : emailRegistro,
			pwd1 : id,
			pwd2 : id
	};

	reqRegistrar.send("p=" + JSON.stringify(p));
}

function cambiarContrasenha() {
	var email = document.getElementById("correo").value;

}