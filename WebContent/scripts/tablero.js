var ficha5 = new Image();
ficha5.src = "imagenes/ficha5.png";

function Tablero() {
	this.casillas = [];
	this.fichas=[];
	this.bordes=[];
	this.crearCasillas();
	this.crearFichas();
	this.crearBordes();
}

Tablero.prototype.crearBordes = function(){
	// Borde Izquierda
	var bordeIzquierda = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeIzquierda.setAttribute('id','lineaBordeIzquierda');
	bordeIzquierda.setAttribute('x1', 60);
	bordeIzquierda.setAttribute('y1', 830);
	bordeIzquierda.setAttribute('x2', 60);
	bordeIzquierda.setAttribute('y2', 30);
	bordeIzquierda.setAttribute("stroke", "black");
	bordeIzquierda.setAttribute("stroke-width", "4");
	this.bordes.push(bordeIzquierda);
	
	// Borde inferior	
	var bordeAbajo = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeAbajo.setAttribute('id','lineaBordeAbajo');
	bordeAbajo.setAttribute('x1', 60);
	bordeAbajo.setAttribute('y1', 830);
	bordeAbajo.setAttribute('x2', 1060);
	bordeAbajo.setAttribute('y2', 830);
	bordeAbajo.setAttribute("stroke", "black");
	bordeAbajo.setAttribute("stroke-width", "4");
	this.bordes.push(bordeAbajo);
	
	// Borde Derecha	
	var bordeDerecho = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeDerecho.setAttribute('id','lineaBordeDerecho');
	bordeDerecho.setAttribute('x1', 1060);
	bordeDerecho.setAttribute('y1', 830);
	bordeDerecho.setAttribute('x2', 1060);
	bordeDerecho.setAttribute('y2', 30);
	bordeDerecho.setAttribute("stroke", "black");
	bordeDerecho.setAttribute("stroke-width", "4");
	this.bordes.push(bordeDerecho);
	
	// Borde superior	
	var bordeSuperior = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeSuperior.setAttribute('id','lineaBordeSuperior');
	bordeSuperior.setAttribute('x1', 1060);
	bordeSuperior.setAttribute('y1', 30);
	bordeSuperior.setAttribute('x2', 60);
	bordeSuperior.setAttribute('y2', 30);
	bordeSuperior.setAttribute("stroke", "black");
	bordeSuperior.setAttribute("stroke-width", "4");
	this.bordes.push(bordeSuperior);
}

Tablero.prototype.dibujar = function(lienzo) {
	// Dibujar casillas
	for (var i = 0; i < 66; i++) {
		this.casillas[i].dibujar(lienzo);
	}
	
	// Dibujar fichas
	for (var i = 0; i < this.fichas.length; i++) {
		this.fichas[i].dibujar(lienzo);
	}
	
	// Dibujar bordes del tablero
	for (var i = 0; i< this.bordes.length; i++){
		try{
			lienzo.appendChild(this.bordes[i]);
		}catch(err){
			console.log("Error al dibujar los bordes del tablero.");
		}
	}
	
}

Tablero.prototype.crearCasillas = function() {
	for (var i = 0; i < 66; i++) {
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
		this.casillas[ocas[i]].imgsrc = "imagenes/oca.png";
	}

	for (var i = 0; i < puentes.length; i++) {
		this.casillas[puentes[i]].tipo = "PUENTE";
		this.casillas[puentes[i]].imgsrc = "imagenes/puente.png";
	}

	for (var i = 0; i < dados.length; i++) {
		this.casillas[dados[i]].tipo = "DADOS";
		this.casillas[dados[i]].imgsrc = "imagenes/dado.png";
	}

	this.casillas[pozo].tipo = "POZO";
	this.casillas[pozo].imgsrc = "imagenes/pozo.png";
	this.casillas[posada].tipo = "POSADA";
	this.casillas[posada].imgsrc = "imagenes/taberna.png";
	this.casillas[carcel].tipo = "CARCEL";
	this.casillas[carcel].imgsrc = "imagenes/carcel.png";
	this.casillas[muerte].tipo = "MUERTE";
	this.casillas[muerte].imgsrc = "imagenes/muerte.png";
	this.casillas[salida].tipo = "SALIDA";
	this.casillas[meta].tipo = "META";

	//Creación de la primera fila
	for (var i = 0; i < 10; i++) {
		this.casillas[i].x0 = i * 100 + 60;
		this.casillas[i].y0 = 730;
		this.casillas[i].xF = this.casillas[i].x0 + 100;
		this.casillas[i].yF = this.casillas[i].y0 + 100;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		
		// Crear bordes		
		// Borde superior
		if(i != 9){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].xF , this.casillas[i].y0, this.casillas[i].y0);
		}

	}
	
	var x0ant = this.casillas[9].x0;
	var xFant = this.casillas[9].xF;
	
	//Creación de la segunda fila
	for (var i = 10; i < 17; i++){
		this.casillas[i].x0 = x0ant;
		this.casillas[i].y0 = this.casillas[i - 1].y0 - 100;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i - 1].y0;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		
		// Crear bordes de la segunda fila
		// Crear bordes izquierda
		if(i != 16){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].x0 , this.casillas[i].y0, this.casillas[i].yF);
		}
		
	}
	
	var y0ant = this.casillas[16].y0;
	var yFant = this.casillas[16].yF;
	
	//Creación de la tercera fila
	for (var i = 17; i < 26; i++){
		this.casillas[i].x0 = this.casillas[i - 1].x0 - 100;
		this.casillas[i].y0 = y0ant;
		this.casillas[i].xF = this.casillas[i - 1].x0;
		this.casillas[i].yF = yFant;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		
		// Crear bordes de la tercera fila
		// Bordes inferiores 
		if(i != 25){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].xF , this.casillas[i].yF, this.casillas[i].yF);
		}
		
	}
	
	x0ant = this.casillas[25].x0;
	xFant = this.casillas[25].xF;
	
	//Creación de la cuarta fila
	for (var i = 26; i < 32; i++){
		this.casillas[i].x0 = x0ant;
		this.casillas[i].y0 = this.casillas[i - 1].y0 + 100;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i].y0 - 100;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		
		// Crear bordes de la cuarta fila
		// Bordes derecha
		if(i != 26 ){
			this.casillas[i].crearLinea(this.casillas[i].xF, this.casillas[i].xF , this.casillas[i].y0, this.casillas[i].yF);
		}
	}
	
	var y0ant = this.casillas[31].y0;
	var yFant = this.casillas[31].yF;
	
	//Creación de la quinta fila
	for (var i = 32; i < 40; i++){
		this.casillas[i].x0 = this.casillas[i - 1].xF;
		this.casillas[i].y0 = y0ant;
		this.casillas[i].xF = this.casillas[i].x0 + 100;
		this.casillas[i].yF = yFant;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		if(i != 39){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].xF , this.casillas[i].y0, this.casillas[i].y0);
		}
	
	}
	
	x0ant = this.casillas[39].x0;
	xFant = this.casillas[39].xF;
	
	//Creacion de la sexta fila
	for (var i = 40; i < 45; i++){
		this.casillas[i].x0 = x0ant;
		this.casillas[i].y0 = this.casillas[i - 1].y0 - 100;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i - 1].y0;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		if(i != 44){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].x0 , this.casillas[i].y0, this.casillas[i].yF);
		}
	}
	
	var y0ant = this.casillas[44].y0;
	var yFant = this.casillas[44].yF;
	
	//Creación de la séptima fila
	for (var i = 45; i < 52; i++){
		this.casillas[i].x0 = this.casillas[i - 1].x0 - 100;
		this.casillas[i].y0 = y0ant;
		this.casillas[i].xF = this.casillas[i - 1].x0;
		this.casillas[i].yF = yFant;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		if(i != 51){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].xF , this.casillas[i].yF, this.casillas[i].yF);
		}
	}
	
	x0ant = this.casillas[51].x0;
	xFant = this.casillas[51].xF;
	
	//Creación de la octava fila
	for (var i = 52; i < 56; i++){
		this.casillas[i].x0 = x0ant;
		this.casillas[i].y0 = this.casillas[i - 1].y0 + 100;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i].y0 - 100;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		if(i != 52 ){
			this.casillas[i].crearLinea(this.casillas[i].xF, this.casillas[i].xF , this.casillas[i].y0, this.casillas[i].yF);
		}
	}
	
	var y0ant = this.casillas[55].y0;
	var yFant = this.casillas[55].yF;
	
	//Creación de la novena fila
	for (var i = 56; i < 62; i++){
		this.casillas[i].x0 = this.casillas[i - 1].xF;
		this.casillas[i].y0 = y0ant;
		this.casillas[i].xF = this.casillas[i].x0 + 100;
		this.casillas[i].yF = yFant;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		if(i != 61){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].xF , this.casillas[i].y0, this.casillas[i].y0);
		}
	}
	
	x0ant = this.casillas[61].x0;
	xFant = this.casillas[61].xF;
	
	//Creación de la décima fila
	for (var i = 62; i < 65; i++){
		this.casillas[i].x0 = x0ant;
		this.casillas[i].y0 = this.casillas[i - 1].y0 - 100;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i - 1].y0;
		this.casillas[i].valor = i;
		this.casillas[i].crearCirculo();
		if(i != 64){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].x0 , this.casillas[i].y0, this.casillas[i].yF);
		}
	}

}

Tablero.prototype.crearFichas = function() {
	for (var i = 0; i < 5; i++) { //algun numero que represente la cantidad de jugadores
		var ficha = new Ficha();
		if(i == 0){
			var x = 85;
			var y = 805;
			ficha.color = ficha.colores[i];
			ficha.crearFicha(x, y);
		}else if(i == 1){
			var x = 85;
			var y = 755;
			ficha.color = ficha.colores[i];
			ficha.crearFicha(x, y);
		}else if(i == 2){
			var x = 135;
			var y = 805;
			ficha.color = ficha.colores[i];
			ficha.crearFicha(x, y);
		}else if(i == 3){
			var x = 135;
			var y = 755;
			ficha.color = ficha.colores[i];
			ficha.crearFicha(x, y);
		}else if(i == 4){
			var x = 110;
			var y = 780;
			ficha.color = ficha.colores[i];
			ficha.crearFicha(x, y);
		}
		this.fichas.push(ficha);
	}
}

function Casilla() {
	this.tipo = "NORMAL";
	this.imgsrc = "";
}

Casilla.prototype.dibujar = function(lienzo) {
	try{
		lienzo.appendChild(this.grp);
	}catch(err){
		console.log("Error al dibujar las casillas.");
	}
	if (typeof this.newLine != 'undefined'){
		try{
			lienzo.appendChild(this.newLine);
		}catch(err){
			console.log("Error al dibujar los bordes de las fichas.");
		}
	}

}

Casilla.prototype.crearCirculo = function() {
	this.grp = document.createElementNS("http://www.w3.org/2000/svg", "g");
		this.rectangle = document.createElementNS("http://www.w3.org/2000/svg", "rect");
		this.rectangle.setAttribute("x", this.x0);
		this.rectangle.setAttribute("y", this.y0);
		this.rectangle.setAttribute("width", 100);
		this.rectangle.setAttribute("height", 100);
		this.rectangle.setAttribute("stroke", "black");
		this.rectangle.setAttribute("stroke-width", "2");
		this.rectangle.setAttribute("fill", "rgb(255, 255, 255)");
	this.grp.appendChild(this.rectangle);
		this.text = document.createElementNS("http://www.w3.org/2000/svg", "text");
		this.text.setAttributeNS(null, "x", this.x0 + 80);
		this.text.setAttributeNS(null, "y", this.y0 + 17);
		this.text.setAttributeNS(null, "font-size", "14");
		this.text.setAttributeNS(null, "fill", "black");
		this.txtValue = document.createTextNode(this.valor);
		this.text.appendChild(this.txtValue);
	this.grp.appendChild(this.text);	
		this.img = document.createElementNS('http://www.w3.org/2000/svg','image');
		this.img.setAttributeNS(null, 'height', '100');
		this.img.setAttributeNS(null, 'width','100');
		this.img.setAttributeNS('http://www.w3.org/1999/xlink', 'href', this.imgsrc);
		this.img.setAttributeNS(null, 'x', this.x0);
		this.img.setAttributeNS(null, 'y', this.y0);
	this.grp.appendChild(this.img);
}


Casilla.prototype.crearLinea = function(xC, xF, yC, yF){
	this.newLine = document.createElementNS('http://www.w3.org/2000/svg','line');
	this.newLine.setAttribute('id','lineaBorde');
	this.newLine.setAttribute('x1', xC);
	this.newLine.setAttribute('y1', yC);
	this.newLine.setAttribute('x2', xF);
	this.newLine.setAttribute('y2', yF);
	this.newLine.setAttribute("stroke", "black");
	this.newLine.setAttribute("stroke-width", "10");
}

function Ficha(){
	this.colores = ["green", "dodgerblue", "red", "gold", "mediumpurple"];
	this.nombreJugador="";
	this.posicion=0;
	this.color = "";
}

Ficha.prototype.crearFicha = function(x, y){
	this.circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circle.setAttribute("cx", x);
	this.circle.setAttribute("cy", y);
	this.circle.setAttribute("r", 12);
	this.circle.setAttribute("fill", this.color);

}

Ficha.prototype.dibujar = function(lienzo) {
	try{
		lienzo.appendChild(this.circle);
	}catch(err){
		console.log("Error al dibujar las fichas.");
	}
}




