import React, {useReducer} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Navbar} from "./components/Navbar";
import {INITIAL_STATE, rootReducer} from "./reducer";
import {HomePage} from "./components/HomePage";
import {UserPage} from "./components/user/UserPage";
import {SocketProvider} from "./SocketContext";

export default function App() {
  const [state, dispatch] = useReducer(rootReducer, INITIAL_STATE);
  const user = state.user;

  return (
    <div className="App">
      <SocketProvider dispatch={dispatch}>
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
