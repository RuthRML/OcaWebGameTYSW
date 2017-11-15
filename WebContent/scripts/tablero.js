function Tablero() {
	this.casillas = [];
	this.fichas=[];
	this.crearCasillas();
	this.crearFichas();
	
}



Tablero.prototype.dibujar = function(lienzo) {
	for (var i = 0; i < 66; i++) {
		this.casillas[i].dibujar(lienzo);
	}
	
	for (var i = 0; i < this.fichas.length; i++) {
		this.fichas[i].dibujar(lienzo);
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

	//Creación de la primera fila
	for (var i = 0; i < 10; i++) {
		this.casillas[i].x0 = i * 50 + 50;
		this.casillas[i].y0 = 400;
		this.casillas[i].xF = this.casillas[i].x0 + 50;
		this.casillas[i].yF = this.casillas[i].y0 + 50;
		this.casillas[i].crearCirculo();
		
		// Crear bordes en la primera fila
		if(i == 0){
			this.casillas[i].crearBordeVertical(this.casillas[i].x0, this.casillas[i].x0, this.casillas[i].y0, this.casillas[i].yF);
		}
		
		if(i != 9){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].xF , this.casillas[i].y0, this.casillas[i].y0);
		}
		
		this.casillas[i].crearBordeHorizontal(this.casillas[i].x0, this.casillas[i].xF, this.casillas[i].yF, this.casillas[i].yF);
		
		if(i == 9){
			this.casillas[i].crearBordeVertical(this.casillas[i].xF, this.casillas[i].xF, this.casillas[i].yF, this.casillas[i].y0);
		}
	}
	
	var x0ant = this.casillas[9].x0;
	var xFant = this.casillas[9].xF;
	
	//Creación de la segunda fila
	for (var i = 10; i < 17; i++){
		this.casillas[i].x0 = x0ant;
		this.casillas[i].y0 = this.casillas[i - 1].y0 - 50;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i - 1].y0;
		this.casillas[i].crearCirculo();
		
		// Crear bordes de la segunda fila
		if(i != 16){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].x0 , this.casillas[i].y0, this.casillas[i].yF);
		}
		
		this.casillas[i].crearBordeVertical(this.casillas[i].xF, this.casillas[i].xF, this.casillas[i].yF, this.casillas[i].y0);
		
		if(i == 16){
			this.casillas[i].crearBordeHorizontal(this.casillas[i].xF, this.casillas[i].x0, this.casillas[i].y0, this.casillas[i].y0);
		}
		
	}
	
	var y0ant = this.casillas[16].y0;
	var yFant = this.casillas[16].yF;
	
	//Creación de la tercera fila
	for (var i = 17; i < 26; i++){
		this.casillas[i].x0 = this.casillas[i - 1].x0 - 50;
		this.casillas[i].y0 = y0ant;
		this.casillas[i].xF = this.casillas[i - 1].x0;
		this.casillas[i].yF = yFant;
		this.casillas[i].crearCirculo();
		
		// Crear bordes de la tercera fila
		if(i != 25){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].xF , this.casillas[i].yF, this.casillas[i].yF);
		}
		
		this.casillas[i].crearBordeHorizontal(this.casillas[i].xF, this.casillas[i].x0, this.casillas[i].y0, this.casillas[i].y0);
		
		if(i == 25){
			this.casillas[i].crearBordeVertical(this.casillas[i].x0, this.casillas[i].x0, this.casillas[i].y0, this.casillas[i].yF);
		}
	}
	
	x0ant = this.casillas[25].x0;
	xFant = this.casillas[25].xF;
	
	//Creación de la cuarta fila
	for (var i = 26; i < 32; i++){
		this.casillas[i].x0 = x0ant;
		this.casillas[i].y0 = this.casillas[i - 1].y0 + 50;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i].y0 - 50;
		this.casillas[i].crearCirculo();
		
		// Crear bordes de la cuarta fila
		if(i != 26 ){
			this.casillas[i].crearLinea(this.casillas[i].xF, this.casillas[i].xF , this.casillas[i].y0, this.casillas[i].yF);
		}
		
		this.casillas[i].crearBordeVertical(this.casillas[i].x0, this.casillas[i].x0, this.casillas[i].y0, this.casillas[i].yF);
	}
	
	var y0ant = this.casillas[31].y0;
	var yFant = this.casillas[31].yF;
	
	//Creación de la quinta fila
	for (var i = 32; i < 40; i++){
		this.casillas[i].x0 = this.casillas[i - 1].xF;
		this.casillas[i].y0 = y0ant;
		this.casillas[i].xF = this.casillas[i].x0 + 50;
		this.casillas[i].yF = yFant;
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
		this.casillas[i].y0 = this.casillas[i - 1].y0 - 50;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i - 1].y0;
		this.casillas[i].crearCirculo();
		if(i != 44){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].x0 , this.casillas[i].y0, this.casillas[i].yF);
		}
	}
	
	var y0ant = this.casillas[44].y0;
	var yFant = this.casillas[44].yF;
	
	//Creación de la séptima fila
	for (var i = 45; i < 52; i++){
		this.casillas[i].x0 = this.casillas[i - 1].x0 - 50;
		this.casillas[i].y0 = y0ant;
		this.casillas[i].xF = this.casillas[i - 1].x0;
		this.casillas[i].yF = yFant;
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
		this.casillas[i].y0 = this.casillas[i - 1].y0 + 50;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i].y0 - 50;
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
		this.casillas[i].xF = this.casillas[i].x0 + 50;
		this.casillas[i].yF = yFant;
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
		this.casillas[i].y0 = this.casillas[i - 1].y0 - 50;
		this.casillas[i].xF = xFant;
		this.casillas[i].yF = this.casillas[i - 1].y0;
		this.casillas[i].crearCirculo();
		if(i != 64){
			this.casillas[i].crearLinea(this.casillas[i].x0, this.casillas[i].x0 , this.casillas[i].y0, this.casillas[i].yF);
		}
	}

}

Tablero.prototype.crearFichas = function() {
	for (var i = 0; i < 2; i++) { //algun numero que represente la cantidad de jugadores
		var ficha = new Ficha();
		ficha.crearFicha(this.lienzo);
		this.fichas.push(ficha);
	}
}



function Casilla() {
	this.tipo = "NORMAL";
}


Casilla.prototype.dibujar = function(lienzo) {
	try{
		lienzo.appendChild(this.rectangle);
	}catch(err){
		
	}
	if (typeof this.newLine != 'undefined'){
		lienzo.appendChild(this.newLine);
	}
	if (typeof this.newBordeHorizontal != 'undefined'){
		lienzo.appendChild(this.newBordeHorizontal);
	}
	if (typeof this.newBordeVertical != 'undefined'){
		lienzo.appendChild(this.newBordeVertical)
	}
}

Casilla.prototype.crearCirculo = function() {
	this.rectangle = document.createElementNS("http://www.w3.org/2000/svg", "rect");
	this.rectangle.setAttribute("x", this.x0);
	this.rectangle.setAttribute("y", this.y0);
	this.rectangle.setAttribute("width", 50);
	this.rectangle.setAttribute("height", 50);
	this.rectangle.setAttribute("stroke", "black");
	this.rectangle.setAttribute("stroke-width", "2");
	this.rectangle.setAttribute("fill", "rgb(255, 255, 255)")
}


Casilla.prototype.crearLinea = function(xC, xF, yC, yF){
	this.newLine = document.createElementNS('http://www.w3.org/2000/svg','line');
	this.newLine.setAttribute('id','lineaBorde');
	this.newLine.setAttribute('x1', xC);
	this.newLine.setAttribute('y1', yC);
	this.newLine.setAttribute('x2', xF);
	this.newLine.setAttribute('y2', yF);
	this.newLine.setAttribute("stroke", "black")
	this.newLine.setAttribute("stroke-width", "10")
}

Casilla.prototype.crearBordeHorizontal = function(xC, xF, yC, yF){
	this.newBordeHorizontal = document.createElementNS('http://www.w3.org/2000/svg','line');
	this.newBordeHorizontal.setAttribute('id','lineaBorde');
	this.newBordeHorizontal.setAttribute('x1', xC);
	this.newBordeHorizontal.setAttribute('y1', yC);
	this.newBordeHorizontal.setAttribute('x2', xF);
	this.newBordeHorizontal.setAttribute('y2', yF);
	this.newBordeHorizontal.setAttribute("stroke", "black")
	this.newBordeHorizontal.setAttribute("stroke-width", "4")
}

Casilla.prototype.crearBordeVertical = function(xC, xF, yC, yF){
	this.newBordeVertical = document.createElementNS('http://www.w3.org/2000/svg','line');
	this.newBordeVertical.setAttribute('id','lineaBorde');
	this.newBordeVertical.setAttribute('x1', xC);
	this.newBordeVertical.setAttribute('y1', yC);
	this.newBordeVertical.setAttribute('x2', xF);
	this.newBordeVertical.setAttribute('y2', yF);
	this.newBordeVertical.setAttribute("stroke", "black")
	this.newBordeVertical.setAttribute("stroke-width", "4")
}


function Ficha(){
	this.nombreJugador="";
	this.posicion=0;
	this.color="green";
}

Ficha.prototype.crearFicha = function(lienzo){
	var x0=75;
	var y0=425;
	var ratio=10;
	this.circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circle.setAttribute("cx", x0);
	this.circle.setAttribute("cy", y0);
	this.circle.setAttribute("r", ratio);
	this.circle.setAttribute("fill", this.color);

}


Ficha.prototype.dibujar = function(lienzo) {
	lienzo.appendChild(this.circle);
}




