import {createContext, Dispatch, ReactNode} from "react";
import {Action} from "./types";
import {makeSocket} from "./socket";

export const SocketContext = createContext(null as WebSocket | null);

export interface SocketProviderProps {
  dispatch: Dispatch<Action>;
  children: ReactNode;
}

export const SocketProvider = (props: SocketProviderProps) => {
  const {dispatch, children} = props;
  const socket = makeSocket(dispatch);

  return (
    <SocketContext.Provider value={socket}>
      {children}
    </SocketContext.Provider>
  );
};
