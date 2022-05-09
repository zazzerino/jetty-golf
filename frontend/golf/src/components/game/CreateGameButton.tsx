import {useContext} from "react";
import {SocketContext} from "../../SocketProvider";
import {sendCreateGame} from "../../socket";

export function CreateGameButton() {
  const socket = useContext(SocketContext);
  if (!socket) return null;

  return (
    <button
      className="CreateGameButton"
      onClick={() => sendCreateGame(socket)}
    >
      Create Game
    </button>
  );
}
