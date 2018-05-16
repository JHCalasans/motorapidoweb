/*
 **********************************************************************************
 Criada por: Augusto (agosto/2005)
 Formata valor numerico
 Exemplo: 1.333.444,00 
 Colocar no OnKeyPress() juntamente com a função Somente_Numero();
 Sintax: OnKeyPress="FormataValor(this,16,event); return Somente_Numero(event);" 
 **********************************************************************************
 */
function FormataValor(campo, tammax, e) {

	var tecla;

	if (window.event) {
		tecla = e.keyCode;
	}
	if (e.which) {
		tecla = e.which;
	}

	vr = campo.value;
	vr = vr.replace("/", "");
	vr = vr.replace("/", "");
	vr = vr.replace(",", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	vr = vr.replace(".", "");
	tam = vr.length;

	// valores das teclas => 8(backspace) - 9(tab) - 48 a 57 / 96 a 105
	// (numeros)

	if (tecla == 9) {
		return;
	}

	// if (tam < tammax && (tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla
	// <= 105 )){ tam = vr.length + 1 ; }
	if (tam < tammax && (tecla >= 48 && tecla <= 57)) {
		tam = vr.length + 1;
	}

	if (tecla == 8 || tecla == 110) {
		tam = tam - 1;
	}

	// if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <=
	// 105 ){
	if (tecla == 8 || tecla >= 48 && tecla <= 57) {

		if (tam <= 2) {
			campo.value = vr;
		}
		if ((tam > 2) && (tam <= 5)) {
			campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 6) && (tam <= 8)) {
			campo.value = vr.substr(0, tam - 5) + '.' + vr.substr(tam - 5, 3)
					+ ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 9) && (tam <= 11)) {
			campo.value = vr.substr(0, tam - 8) + '.' + vr.substr(tam - 8, 3)
					+ '.' + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
		if ((tam >= 12) && (tam <= 14)) {
			campo.value = vr.substr(0, tam - 11) + '.' + vr.substr(tam - 11, 3)
					+ '.' + vr.substr(tam - 8, 3) + '.' + vr.substr(tam - 5, 3)
					+ ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 15) && (tam <= 17)) {
			campo.value = vr.substr(0, tam - 14) + '.' + vr.substr(tam - 14, 3)
					+ '.' + vr.substr(tam - 11, 3) + '.'
					+ vr.substr(tam - 8, 3) + '.' + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
	}
}

// Impede que o usuário digite caracteres não numéricos
function apenasnumero(entry) {
	var str = entry.value;
	for ( var i = 0; i < str.length; i++) {
		var ch = str.substring(i, i + 1);
		if ((ch < "0" || "9" < ch) && ch != '.' && ch != ',') {
			alert("Digite apenas números no Campo.");
			i = str.length;
			entry.value = "";
			entry.focus();
		}
	}
}

//Maiúsculo
function strToUpper(texto){     
    //para usar onkeyup="strToUpper(this)"    
    texto.value = texto.value.toUpperCase();    
}

function formataPercentual(campo, e) {
	// Retira pontos ou vírgulas no começo ou final do campo;
	// Retira pontos ou vírgulas em excesso no campo;
	// Dá replace() de pontos para vírgulas no campo;
	// Uso: (nomeDoCampo, event)
	// Em: onkeypress e onblur;
	// Por: Artur Müller (Janeiro/2013)
	var vrCampo, vrCampoFormatado, vrCampoSplit;
	var tipoEvento;

	tipoEvento = ((window.event) ? tipoEvento = window.event.type : e.type);

	vrCampo = campo.value;

	if (vrCampo.indexOf(",") != -1 || vrCampo.indexOf(".") != -1) {
		if (vrCampo.charAt(0) == "." || vrCampo.charAt(0) == ",") {
			// ajusta começo da string
			vrCampo = vrCampo.replace(".", "");
			vrCampo = vrCampo.replace(",", "");
		}
		if (tipoEvento == "blur") {
			if (vrCampo.charAt(vrCampo.length - 1) == ","
					|| vrCampo.charAt(vrCampo.length - 1) == ".") {
				// ajusta final da string
				vrCampo = vrCampo.substring(0, vrCampo.length - 1);
			}
		}
	}
	// Checa se tem vírgulas ou pontos em excesso
	vrCampoFormatado = vrCampo.replace(".", ",");
	vrCampoSplit = vrCampoFormatado.split(",");
	if (vrCampoSplit.length >= 3) {
		vrCampoFormatado = vrCampoFormatado.split(",", 2);
	}
	campo.value = vrCampoFormatado;
}

function desabilitarFinalDeSemana(data){
	var dia = data.getDay();
	return [(dia != 0 && dia != 6), ''];
}

function formataReal(campo,tammax,teclapres,decimal) {
	var tecla = teclapres.keyCode;
	vr = Limpar(campo.value,"0123456789");
	tam = vr.length;
	dec=decimal

	if (tam < tammax && tecla != 8){ tam = vr.length + 1 ; }

	if (tecla == 8 )
	{ tam = tam - 1 ; }

	if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 )
	{
		if ( tam <= dec )
		{ campo.value = vr ; }
	
		if ( (tam > dec) && (tam <= 5) ){
		campo.value = vr.substr( 0, tam - 2 ) + "," + vr.substr( tam - dec, tam ) ; }
		if ( (tam >= 6) && (tam <= 8) ){
		campo.value = vr.substr( 0, tam - 5 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ;
		}
		if ( (tam >= 9) && (tam <= 11) ){
		campo.value = vr.substr( 0, tam - 8 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; }
		if ( (tam >= 12) && (tam <= 14) ){
		campo.value = vr.substr( 0, tam - 11 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; }
		if ( (tam >= 15) && (tam <= 17) ){
		campo.value = vr.substr( 0, tam - 14 ) + "." + vr.substr( tam - 14, 3 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - 2, tam ) ;}
	}
}

function formataValorReal( value, cifra )
{
 // Se nao for passado sifra, ela fica em branco
 if(cifra == null){cifra = '';}

 // Pegando valor monetario para conversao
 var money = new String( value );

 // Valor armazenado em array
 money = money.split( "." );

 // Valor Real
 var real  = money[0];

 // Valor Decimal
 var decimal = money[1];

 // Incremento de zero na casa decimal de necessario	
 if( decimal.length == 1){ decimal = decimal + '0';}else{decimal = decimal.substring(0,2);}

 // Retornando valor 	
 return cifra + real + ',' + decimal;
}

function formataReal(campo,tammax,teclapres,decimal) {
	var tecla = teclapres.keyCode;
	vr = Limpar(campo.value,"0123456789");
	tam = vr.length;
	dec=decimal

	if (tam < tammax && tecla != 8){ tam = vr.length ; }

	if (tecla == 8 )
	{ tam = tam - 1 ; }

	if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 )
	{
		if ( tam <= dec )
		{ campo.value = vr ; }
	
		if ( (tam > dec) && (tam <= 5) ){
		campo.value = vr.substr( 0, tam - 2 ) + "," + vr.substr( tam - dec, tam ) ; 
		}
		if ( (tam >= 6) && (tam <= 8) ){
		campo.value = vr.substr( 0, tam - 5 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ;
		}
		if ( (tam >= 9) && (tam <= 11) ){
		campo.value = vr.substr( 0, tam - 8 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; }
		if ( (tam >= 12) && (tam <= 14) ){
		campo.value = vr.substr( 0, tam - 11 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; }
		if ( (tam >= 15) && (tam <= 17) ){
		campo.value = vr.substr( 0, tam - 14 ) + "." + vr.substr( tam - 14, 3 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - 2, tam ) ;}
	}
}

function Limpar(valor, validos) {
	// retira caracteres invalidos da string
	var result = "";
	var aux;
	for (var i=0; i < valor.length; i++) {
	aux = validos.indexOf(valor.substring(i, i+1));
	if (aux>=0) {
	result += aux;
	}
	}
	return result;
}

function moeda(valor, casas, separdor_decimal, separador_milhar){ 
	 
	 var valor_total = parseInt(valor * (Math.pow(10,casas)));
	 var inteiros =  parseInt(parseInt(valor * (Math.pow(10,casas))) / parseFloat(Math.pow(10,casas)));
	 var centavos = parseInt(parseInt(valor * (Math.pow(10,casas))) % parseFloat(Math.pow(10,casas)));
	 
	  
	 if(centavos%10 == 0 && centavos+"".length<2 ){
	  centavos = centavos+"0";
	 }else if(centavos<10){
	  centavos = "0"+centavos;
	 }
	  
	 var milhares = parseInt(inteiros/1000);
	 inteiros = inteiros % 1000; 
	 
	 var retorno = "";
	 
	 if(milhares>0){
	  retorno = milhares+""+separador_milhar+""+retorno
	  if(inteiros == 0){
	   inteiros = "000";
	  } else if(inteiros < 10){
	   inteiros = "00"+inteiros; 
	  } else if(inteiros < 100){
	   inteiros = "0"+inteiros; 
	  }
	 }
	 retorno += inteiros+""+separdor_decimal+""+centavos;
	 
	 
	 return retorno;
	 
	}