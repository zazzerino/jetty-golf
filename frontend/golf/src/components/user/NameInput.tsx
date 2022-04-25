import {useContext, useState} from "react";
import {sendUpdateName} from "../../socket";
import {SocketContext} from "../../SocketProvider";

export function NameInput() {
  const [name, setName] = useState("");
  const socket = useContext(SocketContext);

  return (
    <div className="NameInput">
      <>
        <input
          placeholder="Type a username"
          value={name}
          onChange={ev => setName(ev.target.value)}
          onKeyDown={ev => ev.key === "Enter" && send(socket, name)}
        />
        <button onClick={() => send(socket, name)}>
          Send
        </button>
      </>
    </div>
  );
}

function send(socket: WebSocket | null, name: string) {
  socket && sendUpdateName(socket, name);
}