import {Action, AppState, User} from "./types";

const DEFAULT_ID = -1;
const DEFAULT_NAME = "anon";

export const DEFAULT_USER: User = {
  id: DEFAULT_ID,
  name: DEFAULT_NAME,
}

export const INITIAL_STATE: AppState = {
  user: DEFAULT_USER,
}

export function rootReducer(state: AppState, action: Action): AppState {
  switch (action.type) {
    case "SET_USER": return {...state, user: action.user};
  }
}
