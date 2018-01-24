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
	
	// Borde primera fila
	var bordePrimeraFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordePrimeraFila.setAttribute('id', 'lineabordePrimeraFila');
	bordePrimeraFila.setAttribute('x1', 60);
	bordePrimeraFila.setAttribute('y1', 730);
	bordePrimeraFila.setAttribute('x2', 960);
	bordePrimeraFila.setAttribute('y2', 730);
	bordePrimeraFila.setAttribute('stroke', "black");
	bordePrimeraFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordePrimeraFila);
	
	// Borde segunda fila
	var bordeSegundaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeSegundaFila.setAttribute('id', 'lineabordeSegundaFila');
	bordeSegundaFila.setAttribute('x1', 960);
	bordeSegundaFila.setAttribute('y1', 730);
	bordeSegundaFila.setAttribute('x2', 960);
	bordeSegundaFila.setAttribute('y2', 130);
	bordeSegundaFila.setAttribute('stroke', "black");
	bordeSegundaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeSegundaFila);
	
	// Borde tercera fila
	var bordeTerceraFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeTerceraFila.setAttribute('id', 'lineabordeTerceraFila');
	bordeTerceraFila.setAttribute('x1', 960);
	bordeTerceraFila.setAttribute('y1', 130);
	bordeTerceraFila.setAttribute('x2', 160);
	bordeTerceraFila.setAttribute('y2', 130);
	bordeTerceraFila.setAttribute('stroke', "black");
	bordeTerceraFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeTerceraFila);
	
	// Borde cuarta fila
	var bordeCuartaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeCuartaFila.setAttribute('id', 'lineabordeCuartaFila');
	bordeCuartaFila.setAttribute('x1', 160);
	bordeCuartaFila.setAttribute('y1', 130);
	bordeCuartaFila.setAttribute('x2', 160);
	bordeCuartaFila.setAttribute('y2', 630);
	bordeCuartaFila.setAttribute('stroke', "black");
	bordeCuartaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeCuartaFila);
	
	// Borde quinta fila
	var bordeQuintaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeQuintaFila.setAttribute('id', 'lineabordeQuintaFila');
	bordeQuintaFila.setAttribute('x1', 160);
	bordeQuintaFila.setAttribute('y1', 630);
	bordeQuintaFila.setAttribute('x2', 860);
	bordeQuintaFila.setAttribute('y2', 630);
	bordeQuintaFila.setAttribute('stroke', "black");
	bordeQuintaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeQuintaFila);
	
	// Borde sexta fila
	var bordeSextaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeSextaFila.setAttribute('id', 'lineabordeSextaFila');
	bordeSextaFila.setAttribute('x1', 860);
	bordeSextaFila.setAttribute('y1', 630);
	bordeSextaFila.setAttribute('x2', 860);
	bordeSextaFila.setAttribute('y2', 230);
	bordeSextaFila.setAttribute('stroke', "black");
	bordeSextaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeSextaFila);
	
	// Borde séptima fila
	var bordeSeptimaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeSeptimaFila.setAttribute('id', 'lineabordeSeptimaFila');
	bordeSeptimaFila.setAttribute('x1', 860);
	bordeSeptimaFila.setAttribute('y1', 230);
	bordeSeptimaFila.setAttribute('x2', 260);
	bordeSeptimaFila.setAttribute('y2', 230);
	bordeSeptimaFila.setAttribute('stroke', "black");
	bordeSeptimaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeSeptimaFila);
	
	// Borde octava fila
	var bordeOctavaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeOctavaFila.setAttribute('id', 'lineabordeOctavaFila');
	bordeOctavaFila.setAttribute('x1', 260);
	bordeOctavaFila.setAttribute('y1', 230);
	bordeOctavaFila.setAttribute('x2', 260);
	bordeOctavaFila.setAttribute('y2', 530);
	bordeOctavaFila.setAttribute('stroke', "black");
	bordeOctavaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeOctavaFila);
	
	// Borde novena fila
	var bordeNovenaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeNovenaFila.setAttribute('id', 'lineabordeNovenaFila');
	bordeNovenaFila.setAttribute('x1', 260);
	bordeNovenaFila.setAttribute('y1', 530);
	bordeNovenaFila.setAttribute('x2', 760);
	bordeNovenaFila.setAttribute('y2', 530);
	bordeNovenaFila.setAttribute('stroke', "black");
	bordeNovenaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeNovenaFila);
	
	// Borde decima fila
	var bordeDecimaFila = document.createElementNS('http://www.w3.org/2000/svg','line');
	bordeDecimaFila.setAttribute('id', 'lineabordeDecimaFila');
	bordeDecimaFila.setAttribute('x1', 760);
	bordeDecimaFila.setAttribute('y1', 530);
	bordeDecimaFila.setAttribute('x2', 760);
	bordeDecimaFila.setAttribute('y2', 330);
	bordeDecimaFila.setAttribute('stroke', "black");
	bordeDecimaFila.setAttribute('stroke-width', '4');
	this.bordes.push(bordeDecimaFila);
	
}

Tablero.prototype.dibujar = function(lienzo) {
	// Dibujar casillas
	for (var i = 0; i < 65; i++) {
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
	this.casillas[posada].imgsrc = "imagenes/taberna.jpg";
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


Tablero.prototype.actualizarNombresFichas = function(jugadores){
	var listListaJugadores = document.getElementById("listaDeJugadores");
	for (var i = 0; i < jugadores.length; i++) {
	    this.fichas[i].nombreJugador=jugadores[i];
	    var lijugador = document.createElement("li");
	    lijugador.className = "list-group-item";
	    if(this.fichas[i].color == "green"){
	    	lijugador.appendChild(document.createTextNode(jugadores[i] + " es el verde."));
	    	lijugador.setAttribute("id", jugadores[i]);
	    }else if(this.fichas[i].color == "gold"){
	    	lijugador.appendChild(document.createTextNode(jugadores[i] + " es el amarillo."));
	    	lijugador.setAttribute("id", jugadores[i]);
	    }else if(this.fichas[i].color == "red"){
	    	lijugador.appendChild(document.createTextNode(jugadores[i] + " es el rojo."));
	    	lijugador.setAttribute("id", jugadores[i]);
	    }else if(this.fichas[i].color == "mediumpurple"){
	    	lijugador.appendChild(document.createTextNode(jugadores[i] + " es el morado."));
	    	lijugador.setAttribute("id", jugadores[i]);
	    }else{
	    	lijugador.appendChild(document.createTextNode(jugadores[i] + " es el azul."));
	    	lijugador.setAttribute("id", jugadores[i]);
	    }
		listListaJugadores.appendChild(lijugador);
	}
}

Tablero.prototype.actualizarFichas = function(jugador, casillaAnterior, casillaNueva) {
	for (var i = 0; i < 5; i++) { 
		if( this.fichas[i].nombreJugador==jugador){
			
			var casillaAsociadaNueva = this.casillas[casillaNueva];
			var casillaAsociadaAnterior = this.casillas[casillaAnterior]

			if(this.fichas[i].color == "mediumpurple"){
				this.fichas[i].circle.setAttribute("cx", casillaAsociadaNueva.x0+50);
				this.fichas[i].circle.setAttribute("cy", casillaAsociadaNueva.y0+50);
			}else if(this.fichas[i].color == "green"){
				this.fichas[i].circle.setAttribute("cx", casillaAsociadaNueva.x0+25);
				this.fichas[i].circle.setAttribute("cy", casillaAsociadaNueva.y0+75);
			}else if(this.fichas[i].color == "red"){
				this.fichas[i].circle.setAttribute("cx", casillaAsociadaNueva.x0+75);
				this.fichas[i].circle.setAttribute("cy", casillaAsociadaNueva.y0+75);
			}else if(this.fichas[i].color == "gold"){
				this.fichas[i].circle.setAttribute("cx", casillaAsociadaNueva.x0+75);
				this.fichas[i].circle.setAttribute("cy", casillaAsociadaNueva.y0+25);
			}else if(this.fichas[i].color == "dodgerblue"){
				this.fichas[i].circle.setAttribute("cx", casillaAsociadaNueva.x0+25);
				this.fichas[i].circle.setAttribute("cy", casillaAsociadaNueva.y0+25);
			}else{
				console.log("Error al actualizar las fichas.");
			}
			
		}
		
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

function Ficha(){
	this.colores = ["green", "dodgerblue", "red", "gold", "mediumpurple"];
	this.nombreJugador="";
	this.posicion = 0;
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

Tablero.prototype.eliminarExpulsado = function(expulsado){
	for(var i = 0; i<this.fichas.length; i++){
		if(this.fichas[i].nombreJugador == expulsado){
			this.fichas[i].circle.remove();
			var listListaJugadores = document.getElementById("listaDeJugadores");
			var lijugador = document.getElementById(expulsado);
			listListaJugadores.removeChild(lijugador);
		}
	}
}



