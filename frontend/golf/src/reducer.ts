import {Action, AppState, User} from "./types";

export const DEFAULT_NAME = "anon";

export const DEFAULT_USER: User = {
  id: -1,
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
