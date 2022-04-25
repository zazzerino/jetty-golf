import React, {useEffect, useReducer, useRef} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Navbar} from "./components/Navbar";
import {INITIAL_STATE, rootReducer} from "./reducer";
import {HomePage} from "./components/HomePage";
import {UserPage} from "./components/user/UserPage";
import {SocketProvider} from "./SocketProvider";
import {handleMessage} from "./socket";

export default function App(props: {socket: WebSocket}) {
  const socket = props.socket;
  const [state, dispatch] = useReducer(rootReducer, INITIAL_STATE);
  const user = state.user;

  useEffect(() => {
    socket.onmessage = event => handleMessage(dispatch, event);
  }, []);

  return (
    <div className="App">
      <SocketProvider socket={socket}>
        <BrowserRouter>
          <Navbar user={user} />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/user" element={<UserPage user={user} />} />
          </Routes>
        </BrowserRouter>
      </SocketProvider>
    </div>
  );
}
