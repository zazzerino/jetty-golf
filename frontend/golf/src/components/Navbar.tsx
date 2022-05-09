import {Link} from 'react-router-dom';
import {User} from '../types';

function UserInfo(props: {user: User}) {
  const {id, name} = props.user;

  return (
    <p className="UserInfo">
      Logged in as
      <span className="user-name"> {name}</span>
      <span className="user-id">(id={id})</span>
    </p>
  );
}

export function Navbar(props: {user: User}) {
  const user = props.user;

  return (
    <ul className="Navbar">
      <li>
        <Link to="/">
          Home
        </Link>
      </li>
      <li>
        <Link to="/user">
          User
        </Link>
      </li>
      <li>
        <Link to="/game">
          Game
        </Link>
      </li>
      {user && <UserInfo user={user} />}
    </ul>
  );
}
