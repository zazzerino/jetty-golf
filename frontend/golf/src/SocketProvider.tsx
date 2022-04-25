import {createContext, ReactNode} from "react";

export const SocketContext = createContext(null as WebSocket | null);

export const SocketProvider = (props: {socket: WebSocket, children: ReactNode}) => {
  const {socket, children} = props;

  return (
    <SocketContext.Provider value={socket}>
      {children}
    </SocketContext.Provider>
  );
};
