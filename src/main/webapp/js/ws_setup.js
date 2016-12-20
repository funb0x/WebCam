var loc = window.location, new_uri;
if (loc.protocol === "https:") {
	new_uri = "wss:";
} else {
	new_uri = "ws:";
}
new_uri += "//" + loc.host;
new_uri += loc.pathname + "socket/";

var ws = new WebSocket(new_uri);
