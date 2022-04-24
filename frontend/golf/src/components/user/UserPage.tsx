import {NameInput} from "./NameInput";
import {User} from "../../types";

export function UserPage(props: {user: User}) {
  const userId = props.user.id;

  return (
    <div className="UserPage">
      <h2>User</h2>
      <NameInput />
    </div>
  );
}
