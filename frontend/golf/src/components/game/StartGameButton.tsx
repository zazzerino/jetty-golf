import {useContext} from "react";
import {SocketContext} from "../../SocketProvider";
import {sendStartGame} from "../../socket";

export function StartGameButton(props: {gameId: number}) {
  const socket = useContext(SocketContext);
  if (!socket) return null;

  return (
    <button
      className="StartGameButton"
      onClick={() => sendStartGame(socket, props.gameId)}
    >
      Start Game
    </button>
  );
}
