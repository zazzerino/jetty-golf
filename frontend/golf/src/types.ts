import {Dispatch} from "react";

export interface User {
  id: number;
  name: string;
}

export interface AppState {
  user: User;
}

export type Action =
  | {type: "SET_USER", user: User}
  ;

export type RequestType = "UPDATE_NAME";

export interface Request {
  type: RequestType;
}

export interface UpdateNameRequest extends Request {
  type: "UPDATE_NAME";
  name: string;
}

export type ResponseType = "USER" | "GAME";

export interface Response {
  type: ResponseType;
}

export interface UserResponse extends Response {
  type: "USER";
  user: User;
}
