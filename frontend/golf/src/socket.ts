import {Action, Request, UpdateNameRequest, UserResponse} from "./types";
import {Dispatch} from "react";

const WS_URL = "ws://localhost:8080/ws";

export function makeSocket(dispatch: Dispatch<Action>): WebSocket {
  const socket = new WebSocket(WS_URL);
  socket.onopen = onOpen;
  socket.onclose = onClose;
  socket.onerror = onError;
  
  socket.onmessage = (event: MessageEvent) => {
    handleMessage(dispatch, event);
  }
  
  return socket;
}

function onOpen() {
  console.log("websocket connection established");
}

function onClose() {
  console.log("websocket connection closed");
}

function onError(event: Event) {
  console.error("websocket error: " + JSON.stringify(event));
}

function handleMessage(dispatch: Dispatch<Action>, event: MessageEvent) {
  console.log("message received ↓");
  console.log(event.data);
}

function send(socket: WebSocket, request: Request) {
  const text = JSON.stringify(request);
  socket.send(text);
}

export function sendUpdateName(socket: WebSocket, name: string) {
  const request: UpdateNameRequest = {
    type: "UPDATE_NAME",
    name,
  }

  send(socket, request);
}

function handleUserResponse(dispatch: Dispatch<Action>, response: UserResponse) {
  const user = response.user;
  const action: Action = {type: "SET_USER", user};
  dispatch(action);
}
