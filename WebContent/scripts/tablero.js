function Tablero() {
	this.casillas = [];
	this.crearCasillas();
}

Tablero.prototype.dibujar = function(lienzo) {
	for (var i = 0; i < 64; i++) {
		this.casillas[i].dibujar(lienzo);
	}
}

Tablero.prototype.crearCasillas = function() {
	for (var i = 0; i < 64; i++) {
		var casilla = new Casilla();
		this.casillas.push(casilla);
	}

	var ocas = [ 4, 13, 17, 22, 26, 31, 35, 40, 44, 49, 53, 58 ];
	var puentes = [5, 11];
	var pozo = 30;
	var posada = 18;
	var carcel = 51;
	var muerte = 57;
	var dados = [ 25, 52 ];
	var salida = 0;
	var meta = 63;

	for (var i = 0; i < ocas.length; i++) {
		this.casillas[ocas[i]].tipo = "OCA";
	}

	for (var i = 0; i < puentes.length; i++) {
		this.casillas[puentes[i]].tipo = "PUENTE";
	}

	for (var i = 0; i < dados.length; i++) {
		this.casillas[dados[i]].tipo = "DADOS";
	}

	this.casillas[pozo].tipo = "POZO";
	this.casillas[posada].tipo = "POSADA";
	this.casillas[carcel].tipo = "CARCEL";
	this.casillas[muerte].tipo = "MUERTE";
	this.casillas[salida].tipo = "SALIDA";
	this.casillas[meta].tipo = "META";

	for (var i = 0; i < 9; i++) {
		this.casillas[i].x0 = i * 50;
		this.casillas[i].y0 = 0;
		this.casillas[i].xF = this.casillas[i].x0 + 50;
		this.casillas[i].yF = this.casillas[i].y0 + 50;
		this.casillas[i].crearCirculo();
	}

	var x0ant = this.casillas[8].x0;
	var xfant = this.casillas[8].xf;
	
	for (var i = 9; i < 16; i++) {
		if (i == 9) {
			this.casillas[i].x0 = x0ant;
			this.casillas[i].y0 = this.casillas[i - 1].yf;
			this.casillas[i].xF = xfant;
			this.casillas[i].yF = this.casillas[i].y0 + 50;
			this.casillas[i].crearCirculo();
		} else {
			this.casillas[i].x0 = x0ant;
			this.casillas[i].y0 = this.casillas[i-1].yf;
			this.casillas[i].xF = xfant;
			this.casillas[i].yF = this.casillas[i].y0 + 50;
			this.casillas[i].crearCirculo();
		}
	}

}

function Casilla() {
	this.tipo = "NORMAL";
}

Casilla.prototype.dibujar = function(lienzo) {
	lienzo.appendChild(this.rectangle);
}

Casilla.prototype.crearCirculo = function() {
	this.rectangle = document.createElementNS("http://www.w3.org/2000/svg",
			"rect");
	this.rectangle.setAttribute("x", this.x0);
	this.rectangle.setAttribute("y", this.y0);
	this.rectangle.setAttribute("width", 50);
	this.rectangle.setAttribute("height", 50);
	this.rectangle.setAttribute("stroke", "black");
	this.rectangle.setAttribute("stroke-width", "4");
	this.rectangle.setAttribute("fill", "rgb(255, 255, 255)")
}
