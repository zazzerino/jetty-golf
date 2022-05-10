import React, {useContext, useEffect, useReducer} from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Navbar} from "./components/Navbar";
import {INITIAL_STATE, rootReducer} from "./reducer";
import {HomePage} from "./components/HomePage";
import {UserPage} from "./components/user/UserPage";
import {SocketContext} from "./SocketProvider";
import {handleMessage} from "./socket";
import {GamePage} from "./components/game/GamePage";

export default function App() {
  const [state, dispatch] = useReducer(rootReducer, INITIAL_STATE);
  const {user, game} = state;
  const socket = useContext(SocketContext);

  // setup websocket message handler
  useEffect(() => {
    if (socket) {
      socket.onmessage = ev => handleMessage(dispatch, ev);
    }
  }, [socket]);

  const gamePageElem = <GamePage userId={user.id} game={game} />

  return (
    <div className="App">
      <BrowserRouter>
        <Navbar user={user} />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/user" element={<UserPage />} />
          <Route path="/game" element={gamePageElem} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}
