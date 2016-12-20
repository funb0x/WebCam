var upArrow = true;
var downArrow = true;
var leftArrow = true;
var rightArrow = true;

document.onkeydown = function checkKey(e) {

	e = e || window.event;

	if (e.keyCode == '38' && upArrow) {
		// up arrow
		upArrow = false;
		ws.send("UP_P");
	}
	else if (e.keyCode == '40' && downArrow) {
		// down arrow
		downArrow = false;
		ws.send("DOWN_P");
	}
	else if (e.keyCode == '37' && leftArrow) {
		// left arrow
		leftArrow = false;
		ws.send("LEFT_P");
	}
	else if (e.keyCode == '39' && rightArrow) {
		// right arrow
		rightArrow = false;
		ws.send("RIGHT_P");
	}

};

window.onkeyup = function checkKey(e) {

	e = e || window.event;

	if (e.keyCode == '38') {
		// up arrow
		upArrow = true;
		ws.send("UP_R");
	}
	else if (e.keyCode == '40') {
		// down arrow
		downArrow = true;
		ws.send("DOWN_R");
	}
	else if (e.keyCode == '37') {
		// left arrow
		leftArrow = true;
		ws.send("LEFT_R");
	}
	else if (e.keyCode == '39') {
		// right arrow
		rightArrow = true;
		ws.send("RIGHT_R");
	}
};

