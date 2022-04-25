import {Link} from 'react-router-dom';
import {User} from '../types';

function UserInfo(props: {id: number, name: string}) {
  return (
    <p className="user-info">
      Logged in as
      <span className="user-name"> {props.name}</span>
      <span className="user-id">(id={props.id})</span>
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
      {user && <UserInfo id={user.id} name={user.name} />}
    </ul>
  );
}
