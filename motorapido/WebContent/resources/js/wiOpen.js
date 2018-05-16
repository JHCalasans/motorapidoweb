function wiOpen(URL, w, h) {
	var width = (w == undefined) ? 800 : w;
	var height = (h == undefined) ? 600 : h;
	var left = (screen.width / 2) - (width / 2);
	var top = (screen.height / 2) - (height / 2);
	var w = window
			.open(
					URL,
					'janela',
					'width='
							+ width
							+ ', height='
							+ height
							+ ', top='
							+ top
							+ ', left='
							+ left
							+ ', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
	w.focus();
	return w;
}
function checkPopBlock(hidden) {
	var windowName = 'userConsole';
	var popUp = window.open('/RPV/paginas/blank.tjse', windowName,
			'width=1, height=1, left=1, top=1, scrollbars, resizable');
	if (popUp == null || typeof (popUp) == 'undefined') {		
		document.getElementById(hidden).value = 'S';
	} else {		
		popUp.close();
		document.getElementById(hidden).value = 'N';					
	}			
	loadHidden();
}