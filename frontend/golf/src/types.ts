export interface User {
  id: number;
  name: string;
}

export type HandPosition = "BOTTOM" | "LEFT" | "TOP" | "RIGHT";

export interface Player {
  id: number;
  name: string;
  score: number;
  cards: string[];
  uncovered: number[];
  heldCard: string | null;
  handPosition: HandPosition;
}

export type GameState = "INIT" | "UNCOVER_TWO" | "TAKE" | "DISCARD" | "UNCOVER" | "GAME_OVER";

export type CardLocation =
  | "DECK"
  | "TABLE"
  | "HELD"
  | "HAND_0"
  | "HAND_1"
  | "HAND_2"
  | "HAND_3"
  | "HAND_4"
  | "HAND_5";

export interface Game {
  id: number;
  playerId: number;
  tableCards: string[];
  players: Player[];
  hostId: number;
  state: GameState;
  turn: number;
  nextPlayerId: number;
  isFinalTurn: boolean;
  playableCards: CardLocation[];
}

export interface AppState {
  user: User;
  game?: Game;
}

export type Action =
  | {type: "SET_USER", user: User}
  | {type: "SET_GAME", game: Game}
  ;

export type RequestType = "UPDATE_NAME" | "CREATE_GAME";

export interface Request {
  type: RequestType;
}

export interface UpdateNameRequest extends Request {
  type: "UPDATE_NAME";
  name: string;
}

export interface CreateGameRequest extends Request {
  type: "CREATE_GAME";
}

export type ResponseType = "USER" | "GAME";

export interface Response {
  type: ResponseType;
}

export interface UserResponse extends Response {
  type: "USER";
  user: User;
}

export interface GameResponse extends Response {
  type: "GAME";
  game: Game;
}
