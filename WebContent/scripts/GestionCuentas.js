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
				sessionStorage.setItem("nombre", respuesta.nombreUsuario);						
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
					sessionStorage.setItem("nombre", result.nombreUsuario);	
					sessionStorage.setItem("sesion", result.sesion);
					sessionStorage.setItem("correo", result.correo);
					pasarVariable("lobby.html", "login");
				} else {
					alert(result.mensaje);
					console.log(result.mensaje);
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
					sessionStorage.setItem("nombre", result.nombreUsuario);						
					sessionStorage.setItem("sesion", result.sesion);
					sessionStorage.setItem("correo", result.correo);
					pasarVariable("lobby.html", "login");
				} else {
					console.log(result.mensaje);
					alert("Error: " + result.mensaje);
				}
			} else {
				alert("Error con la request.");
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


function pasarVariable(pagina, procedencia){
	pagina += "?";
	pagina += "p=" + procedencia;
	location.href = pagina;
}

function registro() {

	var nombre = document.getElementById("nombre").value;
	var emailRegistro = document.getElementById("correo").value;
	var pass1 = document.getElementById("pwd1").value;
	var pass2 = document.getElementById("pwd2").value;
	var mensajeRegistro = document.getElementById("msgRegistro");

	var reqRegistrar = new XMLHttpRequest();
	reqRegistrar.open("post", "Registrar.jsp");
	reqRegistrar.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	reqRegistrar.onreadystatechange = function() {
		if (reqRegistrar.readyState == 4) {
			if (reqRegistrar.status == 200) {
				var result = JSON.parse(reqRegistrar.responseText);
				if (result.resultado == "OK") {
					mensajeRegistro.innerHTML = "";

					document.getElementById("nombre").value = "";
					document.getElementById("correo").value = "";
					document.getElementById("pwd1").value = "";
					document.getElementById("pwd2").value = "";

					alert("Registro exitoso, " + emailRegistro);

					sessionStorage.setItem("nombre", result.nombreUsuario);
					sessionStorage.setItem("sesion", result.sesion);
					sessionStorage.setItem("correo", emailRegistro);
					pasarVariable("lobby.html", "registro");
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

					document.getElementById("nombre").value = "";
					document.getElementById("correo").value = "";
					document.getElementById("pwd1").value = "";
					document.getElementById("pwd2").value = "";
					
					alert("Registro exitoso, " + emailRegistro);

					sessionStorage.setItem("nombre", result.nombreUsuario);						
					sessionStorage.setItem("sesion", result.sesion);
					sessionStorage.setItem("correo", emailRegistro);
					pasarVariable("lobby.html", "registro");
				} else {
					console.log(result);
					alert("Error: " + result.mensaje);
					mensajeRegistro.innerHTML = result.resultado;
				}
			} else {
				alert("Error con la request.");
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

function recuperarpwd() {
	
	var emailarecuperar = document.getElementById("correo").value;	
	
	var reqRegistrar = new XMLHttpRequest();
	reqRegistrar.open("post", "RecuperarPwd.jsp");
	reqRegistrar.setRequestHeader("Content-Type",
	"application/x-www-form-urlencoded");
	reqRegistrar.onreadystatechange = function() {
		if (reqRegistrar.readyState == 4) {
			if (reqRegistrar.status == 200) {
				var result = JSON.parse(reqRegistrar.responseText);
				if (result.resultado == "OK") {
					alert("Se ha enviado un correo a "+emailarecuperar+" con instrucciones para" +
							"recuperar tu contraseña");
				} else {
					console.log(result);
					alert("Error: " + result.mensaje);
					mensajeRegistro.innerHTML = result.resultado;
				}
			} else {
				alert("Error en la request.");
			}
		}
	};
	var p = {
			tipo : "RECUPERARPWD",
			email : emailarecuperar
	};

	reqRegistrar.send("p=" + JSON.stringify(p));
}

function registrarpwdnueva(){
	
	var pass1 = document.getElementById("pwd1").value;
	var pass2 = document.getElementById("pwd2").value;
	var emailRegistro = getParameterByName('usuario');
	var code = getParameterByName('codigo');

	var reqRegistrar = new XMLHttpRequest();
	reqRegistrar.open("post", "crearpwd.jsp");
	reqRegistrar.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	reqRegistrar.onreadystatechange = function() {
		if (reqRegistrar.readyState == 4) {
			if (reqRegistrar.status == 200) {
				var result = JSON.parse(reqRegistrar.responseText);
				if (result.resultado == "OK") {
					alert("Cambio exitoso, " + emailRegistro);
					sessionStorage.setItem("nombre", result.nombreUsuario);						
					window.location.href='index.html';
				} else {
					alert(result.mensaje);
				}
			} else {
				alert("Error en la request al cambiar contraseña.");
			}
		}
	};
	
	var p = {
			tipo : "NUEVAPWD",
			email : emailRegistro,
			codigo : code,
			pwd1 : pass1,
			pwd2 : pass2			
	};

	reqRegistrar.send("p=" + JSON.stringify(p));
}

function registrarpwdvoluntario(){
	var pass1 = document.getElementById("pwd1").value;
	var pass2 = document.getElementById("pwd2").value;
	var emailRegistro = sessionStorage.getItem("correo");

	var reqRegistrar = new XMLHttpRequest();
	reqRegistrar.open("post", "cambioPwdVoluntario.jsp");
	reqRegistrar.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	reqRegistrar.onreadystatechange = function() {
		if (reqRegistrar.readyState == 4) {
			if (reqRegistrar.status == 200) {
				var result = JSON.parse(reqRegistrar.responseText);
				if (result.resultado == "OK") {
					alert("Cambio de contraseña exitoso, " + emailRegistro);						
					window.location.href='lobby.html';
				} else {
					alert(result.mensaje);
				}
			} else {
				alert("Error en la request al cambiar contraseña.");
			}
		}
	};
	
	var p = {
			tipo : "NUEVAPWDVOLUNTARIO",
			email : emailRegistro,
			pwd1 : pass1,
			pwd2 : pass2			
	};

	reqRegistrar.send("p=" + JSON.stringify(p));

}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}



