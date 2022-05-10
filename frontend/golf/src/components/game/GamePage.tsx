import {CreateGameButton} from "./CreateGameButton";
import {Game} from "../../types";
import {GameCanvas} from "./GameCanvas";

export function GamePage(props: {userId: number, game?: Game}) {
  const {userId, game} = props;

  return (
    <div className="GamePage">
      <h2>Game</h2>
      {game &&
        <GameCanvas userId={userId} game={game} />}
      <CreateGameButton />
    </div>
  );
}
