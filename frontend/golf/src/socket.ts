import {Action, Request, UpdateNameRequest, UserResponse} from "./types";
import {Dispatch} from "react";

const WS_URL = "ws://localhost:8080/ws";

export function makeSocket(): WebSocket {
  const socket = new WebSocket(WS_URL);
  socket.onopen = () => console.log("websocket connection established");
  socket.onclose = () => console.log("websocket connection closed");
  socket.onerror = ev => console.error("websocket error: " + JSON.stringify(ev));
  return socket;
}

export function handleMessage(dispatch: Dispatch<Action>, event: MessageEvent) {
  console.log("message received ↓");
  console.log(event.data);
}

export function sendUpdateName(socket: WebSocket, name: string) {
  const request: UpdateNameRequest = {
    type: "UPDATE_NAME",
    name,
  }

  send(socket, request);
}

function send(socket: WebSocket, request: Request) {
  const json = JSON.stringify(request);
  socket.send(json);
}

function handleUserResponse(dispatch: Dispatch<Action>, response: UserResponse) {
  const user = response.user;
  const action: Action = {type: "SET_USER", user};
  dispatch(action);
}
