const WS_URL = "ws://localhost:8080/ws";
let SOCKET: WebSocket;

export function initWebSocket() {
    if (SOCKET) {
        // do something here
    }

    SOCKET = new WebSocket(WS_URL);
    SOCKET.onopen = onOpen;
    SOCKET.onclose = onClose;
    SOCKET.onerror = onError;
    SOCKET.onmessage = onMessage;
}

function onOpen() {
    console.log("websocket connection established");
}

function onClose() {
    console.log("websocket connection closed");
}

function onError(event: Event) {
    console.error("WEBSOCKET ERROR: " + JSON.stringify(event));
}

function onMessage(event: MessageEvent) {
    console.log("message received ↓");
    console.log(event.data);
}
