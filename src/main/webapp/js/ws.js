$(document).ready(function() {

	var loc = window.location, new_uri;
	if (loc.protocol === "https:") {
		new_uri = "wss:";
	} else {
		new_uri = "ws:";
	}
	new_uri += "//" + loc.host;
	new_uri += loc.pathname + "socket/";

	var ws = new WebSocket(new_uri);

	ws.onopen = function(e) {
		if (typeof console !== 'undefined') {
			console.info('WS open');
		}
	};

	ws.onmessage = function (e) {
		
		var data = JSON.parse(e.data),
			type = data.type,
			$cam = $('#cam');

		if (typeof console !== 'undefined') {
			console.info('WS message', type);
		}

		if (type === 'image') {
			$cam.attr("src", "data:image/jpeg;base64," + data.image).trigger("change");
			setTimeout(function() {
				$cam.removeClass('shadow').trigger("change");
			}, 1000);
		}
	};

	ws.onclose = function() {
		if (typeof console !== 'undefined') {
			console.info('WS close');
		}
	};

	ws.onerror = function(err) {
		if (typeof console !== 'undefined') {
			console.info('WS error');
		}
	};
});




