import {
  Action,
  CreateGameRequest,
  GameResponse,
  Request,
  Response,
  StartGameRequest,
  UpdateNameRequest,
  UserResponse
} from "./types";
import {Dispatch} from "react";

const WS_URL = "ws://localhost:8080/ws";

export function makeSocket(): WebSocket {
  const socket = new WebSocket(WS_URL);

  socket.onopen = () => console.log("websocket connection opened");
  socket.onclose = () => console.log("websocket connection closed");
  socket.onerror = ev => console.error("websocket error: " + JSON.stringify(ev));

  return socket;
}

export function handleMessage(dispatch: Dispatch<Action>, event: MessageEvent) {
  const response = JSON.parse(event.data) as Response;
  console.log("message received ↓");
  console.log(response);

  switch (response.type) {
    case "USER": return handleUserResponse(dispatch, response as UserResponse);
    case "GAME": return handleGameResponse(dispatch, response as GameResponse);
  }
}

function handleUserResponse(dispatch: Dispatch<Action>, response: UserResponse) {
  const user = response.user;
  const action: Action = {type: "SET_USER", user};

  dispatch(action);
}

function handleGameResponse(dispatch: Dispatch<Action>, response: GameResponse) {
  const game = response.game;
  const action: Action = {type: "SET_GAME", game};

  dispatch(action);
}

export function sendUpdateName(socket: WebSocket, name: string) {
  const request: UpdateNameRequest = {
    type: "UPDATE_NAME",
    name,
  }

  send(socket, request);
}

export function sendCreateGame(socket: WebSocket) {
  const request: CreateGameRequest = {type: "CREATE_GAME"};
  send(socket, request);
}

export function sendStartGame(socket: WebSocket, gameId: number) {
  const request: StartGameRequest = {
    type: "START_GAME",
    gameId,
  };

  send(socket, request);
}

function send(socket: WebSocket, request: Request) {
  const json = JSON.stringify(request);
  socket.send(json);
}
